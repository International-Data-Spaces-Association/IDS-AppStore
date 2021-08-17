package de.fraunhofer.fit.appstore.services.registry.webhook;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.fraunhofer.fit.appstore.exceptions.TemplateException;
import de.fraunhofer.fit.appstore.exceptions.WebhookException;
import de.fraunhofer.fit.appstore.model.registry.webhook.Event;
import de.fraunhofer.fit.appstore.model.registry.webhook.ScanOverview;
import de.fraunhofer.fit.appstore.services.template.ContainerTemplateService;
import io.dataspaceconnector.common.exception.NotImplemented;
import io.dataspaceconnector.model.app.AppDesc;
import io.dataspaceconnector.service.resource.type.AppService;
import io.dataspaceconnector.service.resource.type.ArtifactService;
import io.dataspaceconnector.service.resource.type.RepresentationService;
import io.dataspaceconnector.service.resource.type.ResourceService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * Service for managing registry webhooks.
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class RegistryWebhookService {

    /**
     * The app resource service.
     */
    private final @NonNull ResourceService resourceSvc;

    /**
     * The representation service.
     */
    private final @NonNull RepresentationService representationSvc;

    /**
     * The data app service.
     */
    private final @NonNull AppService appSvc;

    /**
     * Service for container templates.
     */
    private final @NonNull ContainerTemplateService templateSvc;

    /**
     * The artifact service.
     */
    private final @NonNull ArtifactService artifactSvc;

    /**
     * Process an incoming registry event.
     *
     * @param event The event.
     * @throws WebhookException if the event does not match the know event types.
     */
    public void processRegistryEvent(final Event event) throws WebhookException {
        // Validate event before passing it to the right processor.
        RegistryWebhookUtils.validateEvent(event);

        try {
            switch (event.getType()) {
                case PUSH_ARTIFACT:
                    this.pushArtifactEventHandler(event);
                    break;
                case PULL_ARTIFACT:
                    this.pullArtifactEventHandler(event);
                    break;
                case DELETE_ARTIFACT:
                    this.deleteArtifactEventHandler(event);
                    break;
                case UPLOAD_CHART:
                case DOWNLOAD_CHART:
                case DELETE_CHART:
                    // TODO: FUTURE FEATURE FOR HELM CHARTS
                    break;
                case SCANNING_COMPLETED:
                    this.scanningCompletedEventHandler(event);
                    break;
                case SCANNING_FAILED:
                    this.scanningFailedEventHandler(event);
                    break;
                case QUOTA_EXCEED:
                case QUOTA_WARNING:
                case REPLICATION:
                    // TODO: NO NEED TO IMPLEMENT NOW
                    break;
                default:
                    throw new WebhookException("The received event is not a regular harbor "
                            + "registry event. Not a valid event!");
            }
        } catch (Exception ignored) {
            // Nothing to do here.
        }
    }

    /**
     * Handle push artifact events.
     *
     * @param event The event.
     * @throws WebhookException if event processing failed.
     */
    private void pushArtifactEventHandler(final Event event) throws WebhookException,
            TemplateException {
        // REPOSITORY
        final var created = event.getEventData().getRepository().getDateCreated();
        final var repoName = event.getEventData().getRepository().getName();
        final var repoNamespace = event.getEventData().getRepository().getNamespace();
        final var repoFullName = event.getEventData().getRepository().getRepoFullName();
        final var repoType = event.getEventData().getRepository().getRepoType();

        // RESOURCE
        final var resources = event.getEventData().getResources();

        String resourceDigest = null;
        String resourceTag;
        String resourceUrl;

        if (resources != null && resources.length > 1) {
            final var resource = resources[0];
            resourceDigest = resource.getDigest();
            resourceTag = resource.getTag();
            resourceUrl = resource.getResourceUrl();
        }

        // ResourceId is == repoName
        final var resourceId = UUID.fromString(repoName);

        // Get app by id. Throws ResourceNotFoundException if no app could be found.
        // TODO: Here resource id ia the uuid. how to get the whole uri

        final var resource = resourceSvc.get(resourceId);
        final var representation = resource.getRepresentations().get(0);
        final var app = representation.getDataApps().get(0);

        // Create new desc with event data.
        final var appDesc = new AppDesc();
        appDesc.setDocs(app.getDocs());
        appDesc.setRemoteId(app.getRemoteId());
        appDesc.setEnvironmentVariables(app.getEnvironmentVariables());
        appDesc.setStorageConfig(app.getStorageConfig());
        appDesc.setSupportedUsagePolicies(app.getSupportedUsagePolicies());

        appDesc.setSecurityScannerName(app.getSecurityScannerName());
        appDesc.setSecurityScannerVendor(app.getSecurityScannerVendor());
        appDesc.setSecurityScannerVersion(app.getSecurityScannerVersion());
        appDesc.setSecurityScannerCompletePercent(app.getSecurityScannerCompletePercent());
        appDesc.setSecurityScannerIssuesTotal(app.getSecurityScannerIssuesTotal());
        appDesc.setSecurityScannerIssuesFixable(app.getSecurityScannerIssuesFixable());
        appDesc.setSecurityScannerIssuesLow(app.getSecurityScannerIssuesLow());
        appDesc.setSecurityScannerIssuesMedium(app.getSecurityScannerIssuesMedium());
        appDesc.setSecurityScannerIssuesHigh(app.getSecurityScannerIssuesHigh());

        RegistryWebhookUtils.updateRepositoryName(app, appDesc, repoName);
        RegistryWebhookUtils.updateRepositoryNamespace(app, appDesc, repoNamespace);
        RegistryWebhookUtils.updateRepositoryDigest(app, appDesc, resourceDigest);

        appSvc.update(app.getId(), appDesc);

        // Create and store template.
        final var template = templateSvc.createContainerTemplate(resourceId);
        final var artifact = representation.getArtifacts().get(0);

        try {
            final var objectMapper = new ObjectMapper();
            final var data =
                    objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(template);

            // Set artifact data.
            artifactSvc.setData(artifact.getId(),
                    new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8)));
        } catch (JsonProcessingException e) {
            if (log.isWarnEnabled()) {
                log.warn("Failed to deserialize template. [exception=({})]", e.getMessage());
            }
            throw new TemplateException("Failed to store template.");
        } catch (IOException exception) {
            if (log.isWarnEnabled()) {
                log.warn("Failed to store template. [exception=({})]", exception.getMessage());
            }
            throw new TemplateException("Failed to store template.");
        }
    }

    /**
     * Handle pull artifact events.
     *
     * @param event The event.
     * @throws WebhookException if event processing failed.
     */
    private void pullArtifactEventHandler(final Event event) throws WebhookException {
        throw new NotImplemented(); // TODO implement and remove
    }

    /**
     * Handle delete artifact events.
     *
     * @param event The event.
     * @throws WebhookException if event processing failed.
     */
    private void deleteArtifactEventHandler(final Event event) throws WebhookException {
        throw new NotImplemented(); // TODO implement and remove
    }

    /**
     * Handle scanning completed events.
     *
     * @param event The event.
     * @throws WebhookException if event processing failed.
     */
    private void scanningCompletedEventHandler(final Event event) throws WebhookException {
        // REPOSITORY
        final var repoName = event.getEventData().getRepository().getName();
        final var repoNamespace = event.getEventData().getRepository().getNamespace();
        final var repoFullName = event.getEventData().getRepository().getRepoFullName();
        final var repoType = event.getEventData().getRepository().getRepoType();

        // RESOURCE
        final var resources = event.getEventData().getResources();

        String resourceDigest = null;
        String resourceTag = null;
        String resourceUrl = null;
        ScanOverview resourceScanOverview = null;

        if (resources != null && resources.length > 1) {
            final var resource = resources[0];
            resourceDigest = resource.getDigest();
            resourceTag = resource.getTag();
            resourceUrl = resource.getResourceUrl();
            resourceScanOverview = resource.getScanOverview();
        }

        if (resourceScanOverview == null
                || resourceScanOverview.getSummary() == null
                || resourceScanOverview.getSummary().getSummary() == null
                || resourceScanOverview.getScanner() == null) {
            throw new WebhookException("The Event SCANNING or parts of it cannot be null. Not a "
                    + "valid SecurityScanEvent!");
        }

        // ResourceId is == repoName
        final var resourceId = UUID.fromString(repoName);

        // Get app by id. Throws ResourceNotFoundException if no app could be found.
        final var resource = resourceSvc.get(resourceId);
        final var representation = resource.getRepresentations().get(0);
        final var app = representation.getDataApps().get(0);

        // Create new desc with event data.
        final var appDesc = new AppDesc();
        appDesc.setDocs(app.getDocs());
        appDesc.setRemoteId(app.getRemoteId());
        appDesc.setEnvironmentVariables(app.getEnvironmentVariables());
        appDesc.setStorageConfig(app.getStorageConfig());
        appDesc.setSupportedUsagePolicies(app.getSupportedUsagePolicies());

        final var summary = resourceScanOverview.getSummary();
        final var summarySummary = resourceScanOverview.getSummary().getSummary();
        final var scanner = resourceScanOverview.getScanner();

        appDesc.setSecurityScannerCompletePercent(resourceScanOverview.getCompletePercent());

        // TODO: ADD SCANSTATUS TO APP ENTITY TO SHOW IF SCAN FAILED OR NOT

        // Summary
        appDesc.setSecurityScannerIssuesTotal(summary.getTotal());
        appDesc.setSecurityScannerIssuesFixable(summary.getFixable());

        // Summary Summary
        appDesc.setSecurityScannerIssuesLow(summarySummary.getLow());
        appDesc.setSecurityScannerIssuesMedium(summarySummary.getMedium());
        appDesc.setSecurityScannerIssuesHigh(summarySummary.getHigh());

        // Scanner
        appDesc.setSecurityScannerName(scanner.getName());
        appDesc.setSecurityScannerVendor(scanner.getVendor());
        appDesc.setSecurityScannerVersion(scanner.getVersion());

        RegistryWebhookUtils.updateRepositoryName(app, appDesc, repoName);
        RegistryWebhookUtils.updateRepositoryNamespace(app, appDesc, repoNamespace);
        RegistryWebhookUtils.updateRepositoryDigest(app, appDesc, resourceDigest);

        appSvc.update(app.getId(), appDesc);
    }

    /**
     * Handle scanning failed events.
     *
     * @param event The event.
     * @throws WebhookException if event processing failed.
     */
    private void scanningFailedEventHandler(final Event event) throws WebhookException {
        // REPOSITORY
        final var repoName = event.getEventData().getRepository().getName();
        final var repoNamespace = event.getEventData().getRepository().getNamespace();
        final var repoFullName = event.getEventData().getRepository().getRepoFullName();
        final var repoType = event.getEventData().getRepository().getRepoType();

        // RESOURCE
        final var resources = event.getEventData().getResources();

        String resourceDigest = null;
        String resourceTag = null;
        String resourceUrl = null;
        ScanOverview resourceScanOverview = null;

        if (resources != null && resources.length > 1) {
            final var resource = resources[0];
            resourceDigest = resource.getDigest();
            resourceTag = resource.getTag();
            resourceUrl = resource.getResourceUrl();
            resourceScanOverview = resource.getScanOverview();
        }

        if (resourceScanOverview == null || resourceScanOverview.getScanner() == null) {
            throw new WebhookException("The Event SCANNING or parts of it cannot be null. Not a "
                    + "valid SecurityScanEvent!");
        }

        // ResourceId is == repoName
        final var resourceId = UUID.fromString(repoName);

        // Get app by id. Throws ResourceNotFoundException if no app could be found.
        final var resource = resourceSvc.get(resourceId);
        final var representation = resource.getRepresentations().get(0);
        final var app = representation.getDataApps().get(0);

        // Create new desc with event data.
        final var appDesc = new AppDesc();
        appDesc.setDocs(app.getDocs());
        appDesc.setRemoteId(app.getRemoteId());
        appDesc.setEnvironmentVariables(app.getEnvironmentVariables());
        appDesc.setStorageConfig(app.getStorageConfig());
        appDesc.setSupportedUsagePolicies(app.getSupportedUsagePolicies());

        final var scanner = resourceScanOverview.getScanner();
        appDesc.setSecurityScannerCompletePercent(resourceScanOverview.getCompletePercent());

        // TODO: ADD SCANSTATUS TO APP ENTITY TO SHOW IF SCAN FAILED OR NOT

        // Scanner
        appDesc.setSecurityScannerName(scanner.getName());
        appDesc.setSecurityScannerVendor(scanner.getVendor());
        appDesc.setSecurityScannerVersion(scanner.getVersion());

        RegistryWebhookUtils.updateRepositoryName(app, appDesc, repoName);
        RegistryWebhookUtils.updateRepositoryNamespace(app, appDesc, repoNamespace);
        RegistryWebhookUtils.updateRepositoryDigest(app, appDesc, resourceDigest);

        appSvc.update(app.getId(), appDesc);
    }

}

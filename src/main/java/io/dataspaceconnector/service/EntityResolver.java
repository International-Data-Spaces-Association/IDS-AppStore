/*
 * Copyright 2020-2022 Fraunhofer Institute for Software and Systems Engineering
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.dataspaceconnector.service;

import de.fraunhofer.iais.eis.ContractAgreement;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.dataspaceconnector.common.exception.ErrorMessage;
import io.dataspaceconnector.common.ids.mapping.RdfConverter;
import io.dataspaceconnector.common.net.QueryInput;
import io.dataspaceconnector.common.util.Utils;
import io.dataspaceconnector.common.exception.InvalidResourceException;
import io.dataspaceconnector.model.agreement.Agreement;
import io.dataspaceconnector.model.app.App;
import io.dataspaceconnector.model.artifact.Artifact;
import io.dataspaceconnector.model.base.Entity;
import io.dataspaceconnector.model.catalog.Catalog;
import io.dataspaceconnector.model.contract.Contract;
import io.dataspaceconnector.model.representation.Representation;
import io.dataspaceconnector.model.resource.OfferedResource;
import io.dataspaceconnector.model.resource.OfferedResourceDesc;
import io.dataspaceconnector.model.resource.RequestedResource;
import io.dataspaceconnector.model.resource.RequestedResourceDesc;
import io.dataspaceconnector.model.rule.ContractRule;
import io.dataspaceconnector.common.ids.DeserializationService;
import io.dataspaceconnector.service.resource.ids.builder.*;
import io.dataspaceconnector.service.resource.type.*;
import io.dataspaceconnector.common.usagecontrol.AllowAccessVerifier;
import io.dataspaceconnector.config.BasePath;
import io.dataspaceconnector.common.net.EndpointUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * This service offers methods for finding entities by their identifying URI.
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class EntityResolver {

    /**
     * Service for artifacts.
     */
    private final @NonNull ArtifactService artifactService;

    /**
     * Service for representations.
     */
    private final @NonNull RepresentationService representationService;

    /**
     * Service for offered resources.
     */
    private final @NonNull ResourceService<OfferedResource, OfferedResourceDesc> offerService;

    /**
     * Service for requested resources.
     */
    private final @NonNull ResourceService<RequestedResource, RequestedResourceDesc> requestService;

    /**
     * Service for catalogs.
     */
    private final @NonNull CatalogService catalogService;

    /**
     * Service for catalogs.
     */
    private final @NonNull AppService appService;


    /**
     * Service for contract offers.
     */
    private final @NonNull ContractService contractService;

    /**
     * Service for contract rules.
     */
    private final @NonNull RuleService ruleService;

    /**
     * Service for contract agreements.
     */
    private final @NonNull AgreementService agreementService;

    /**
     * Service for building ids objects.
     */
    private final @NonNull IdsCatalogBuilder catalogBuilder;

    /**
     * Service for building ids resource.
     */
    private final @NonNull IdsResourceBuilder<OfferedResource> offerBuilder;

    /**
     * Service for building ids artifact.
     */
    private final @NonNull IdsArtifactBuilder artifactBuilder;

    /**
     * Service for building ids representation.
     */
    private final @NonNull IdsRepresentationBuilder representationBuilder;

    /**
     * Service for building ids contract.
     */
    private final @NonNull IdsContractBuilder contractBuilder;

    /**
     * Service for building ids contract.
     */
    private final @NonNull IdsDataAppBuilder dataAppBuilder;


    /**
     * Skips the data access verification.
     */
    private final @NonNull AllowAccessVerifier allowAccessVerifier;

    /**
     * Performs a artifact requests.
     */
    private final @NonNull ArtifactRetriever artifactReceiver;

    /**
     * Service for deserialization.
     */
    private final @NonNull DeserializationService deserializationService;

    /**
     * Return any connector entity by its id.
     *
     * @param elementId The entity id.
     * @return The respective object.
     * @throws IllegalArgumentException If the resource is null or the elementId.
     */
    @SuppressFBWarnings(value = "REC_CATCH_EXCEPTION",
            justification = "exceptions are checked at a higher level")
    public Optional<Entity> getEntityById(final URI elementId) {
        Utils.requireNonNull(elementId, ErrorMessage.URI_NULL);

        try {
            final var endpointId = EndpointUtils.getEndpointIdFromPath(elementId);
            final var basePath = endpointId.getBasePath();
            final var entityId = endpointId.getResourceId();

            if (basePath.contains(BasePath.ARTIFACTS)) {
                return Optional.of(artifactService.get(entityId));
            } else if (basePath.contains(BasePath.REPRESENTATIONS)) {
                return Optional.of(representationService.get(entityId));
            } else if (basePath.contains(BasePath.OFFERS)) {
                return Optional.of(offerService.get(entityId));
            } else if (basePath.contains(BasePath.CATALOGS)) {
                return Optional.of(catalogService.get(entityId));
            } else if (basePath.contains(BasePath.CONTRACTS)) {
                return Optional.of(contractService.get(entityId));
            } else if (basePath.contains(BasePath.RULES)) {
                return Optional.of(ruleService.get(entityId));
            } else if (basePath.contains(BasePath.AGREEMENTS)) {
                return Optional.of(agreementService.get(entityId));
            } else if (basePath.contains(BasePath.REQUESTS)) {
                return Optional.of(requestService.get(entityId));
            } else if (basePath.contains(BasePath.APPS)) {
                return Optional.of(appService.get(entityId));
            }
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    /**
     * Translate a connector entity to an ids rdf string.
     *
     * @param <T>    Type of the entity.
     * @param entity The connector's entity.
     * @return A rdf string of an ids object.
     */
    @SuppressFBWarnings(value = "REC_CATCH_EXCEPTION",
            justification = "exceptions are checked at a higher level")
    public <T extends Entity> String getEntityAsRdfString(final T entity)
            throws InvalidResourceException {
        // NOTE Maybe the builder class could be found without the ugly if array?
        try {
            if (entity instanceof Artifact artifactEntity) {
                final var artifact = artifactBuilder.create(artifactEntity);
                return RdfConverter.toRdf(Objects.requireNonNull(artifact));
            } else if (entity instanceof OfferedResource offeredResource) {
                final var resource = offerBuilder.create(offeredResource);
                return RdfConverter.toRdf(Objects.requireNonNull(resource));
            } else if (entity instanceof Representation representationEntity) {
                final var representation = representationBuilder.create(representationEntity);
                return RdfConverter.toRdf(Objects.requireNonNull(representation));
            } else if (entity instanceof Catalog catalogEntity) {
                final var catalog = catalogBuilder.create(catalogEntity);
                return RdfConverter.toRdf(Objects.requireNonNull(catalog));
            } else if (entity instanceof Contract contract) {
                final var contractOffer = contractBuilder.create(contract);
                return RdfConverter.toRdf(Objects.requireNonNull(contractOffer));
            } else if (entity instanceof Agreement agreement) {
                return agreement.getValue();
            } else if (entity instanceof ContractRule contractRule) {
                return contractRule.getValue();
            }else if (entity instanceof App appEntity) {
                final var app = dataAppBuilder.create(appEntity);
                return RdfConverter.toRdf(Objects.requireNonNull(app));
            }
        } catch (Exception exception) {
            // If we do not allow requesting an object type, respond with exception.
            if (log.isWarnEnabled()) {
                log.warn("Could not provide ids object. [entity=({}), exception=({})]",
                        entity, exception.getMessage(), exception);
            }
            throw new InvalidResourceException("No provided description for requested element.");
        }

        // If we do not allow requesting an object type, respond with exception.
        if (log.isDebugEnabled()) {
            log.debug("Not a requestable ids object. [entity=({})]", entity);
        }
        throw new InvalidResourceException("No provided description for requested element.");
    }

    /**
     * Return artifact by uri. This will skip the access control.
     *
     * @param requestedArtifact The artifact uri.
     * @param queryInput        Http query for data request.
     * @return Artifact from database.
     * @throws IOException if the data cannot be received.
     */
    public InputStream getDataByArtifactId(final URI requestedArtifact,
                                           final QueryInput queryInput)
            throws IOException {
        final var endpoint = EndpointUtils.getUUIDFromPath(requestedArtifact);
        return artifactService.getData(allowAccessVerifier, artifactReceiver, endpoint, queryInput,
                null);
    }

    /**
     * Get stored contract agreement for requested element.
     *
     * @param target The requested element.
     * @return The respective contract agreement.
     */
    public List<ContractAgreement> getContractAgreementsByTarget(final URI target) {
        final var uuid = EndpointUtils.getUUIDFromPath(target);
        final var artifact = artifactService.get(uuid);

        final var agreements = artifact.getAgreements();
        final var agreementList = new ArrayList<ContractAgreement>();
        for (final var agreement : agreements) {
            final var value = agreement.getValue();
            final var idsAgreement = deserializationService.getContractAgreement(value);
            agreementList.add(idsAgreement);
        }
        return agreementList;
    }
}

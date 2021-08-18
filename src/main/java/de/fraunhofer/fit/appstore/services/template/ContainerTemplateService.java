package de.fraunhofer.fit.appstore.services.template;

import de.fraunhofer.fit.appstore.exceptions.TemplateException;
import de.fraunhofer.fit.appstore.model.portainer.Template;
import io.dataspaceconnector.model.app.App;
import io.dataspaceconnector.model.representation.Representation;
import io.dataspaceconnector.model.resource.Resource;
import io.dataspaceconnector.service.resource.type.ResourceService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Provides container template handling.
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class ContainerTemplateService {

    /**
     * The resource service.
     */
    private final @NonNull ResourceService resourceService;

    /**
     * Create backend container template by resource id.
     *
     * @param resourceId The resource id.
     * @return The template.
     * @throws TemplateException if the template could not be created.
     */
    public Template createContainerTemplate(final UUID resourceId)
            throws TemplateException {
        final var resource = resourceService.get(resourceId);
        final var representation = resource.getRepresentations().get(0);
        final var app = representation.getDataApps().get(0);

        // TODO: CHECK FOR IMAGE IN REGISTRY

        return createContainerTemplate(resource, representation, app);
    }

    // TODO: Take a Look at the template generation

    /**
     * Create container template from resource, representation, and app.
     *
     * @param res The app resource.
     * @param rep The representation.
     * @param app The data app.
     * @return The template.
     */
    private Template createContainerTemplate(final Resource res, final Representation rep,
                                             final App app) {
        final var template = new Template();

        // Template Type 1 for Docker Container / 2 for Docker Swarm.
        template.setType(1);

        ContainerTemplateUtils.setTemplateTitle(app, rep, template, res);

        // This is what the actual container is named. Should be resourceId.
        template.setName(res.getId().toString());

        // Image Name ("image": "linuxserver/headphones:latest"
        // TODO: check elements if null on this point
        if (!app.getRepositoryNameSpace().isBlank() || !app.getRepositoryName().isBlank()) {
            // IF work with versioned resources
            // template.setImage(app.getRepositoryNameSpace() + "/" + app.getRepositoryName() +
            // ":" + res.getVersion());
            template.setImage(app.getRepositoryNameSpace() + "/" + app.getRepositoryName());
        }

        // Registry TODO: Check elements if null on this point
        if (!rep.getDistributionService().toString().isBlank()
                || !app.getRepositoryNameSpace().isBlank() || !app.getRepositoryName().isBlank()) {
            template.setRegistry(URI.create(String.format("%s/%s/%s",
                    rep.getDistributionService().toString(),
                    app.getRepositoryNameSpace(),
                    app.getRepositoryName())));
        }

        ContainerTemplateUtils.setTemplateDescription(app, rep, template, res);

        try {
            template.setLogo(new URI("https://logo_placeholder.example"));
        } catch (URISyntaxException e) {
            if (log.isWarnEnabled()) {
                log.warn("Could not set logo to template. [exception=({})]", e.getMessage());
            }
        }

        template.setCategories(new ArrayList<>(res.getKeywords()));
        template.setPlatform("linux");
        template.setRestartPolicy("always");

        // PORTS
        /*
        {
            "ports": ["8080:80/tcp", "443/tcp"]
        }
        // Labeled Ports
        {
          "ports": [
            {
              "WebUI": "8096:8096/tcp",
              "HTTPS WebUI": "8920:8920/tcp",
              "DNLA": "1900:1900/udp",
              "Discovery": "7359:7359/udp"
            }
          ],
        }
         */

        // VOLUMES
        /*
        {
          "volumes": [
            {
              "container": "/data",
              "bind": "!downloads"
            },
            {
              "container": "/etc/localtime",
              "bind": "/etc/localtime"
            },
              "container": "/config",
              "bind": "app_config"
          ],
        }
         */

        // Environment Variables
        // {
        //  "name": "the name of the environment variable, as supported in the container image
        //  (mandatory)",
        //  "label": "label for the input in the UI (mandatory unless set is present)",
        //  "description": "a short description for this input, will be available as a tooltip in
        //  the UI (optional)",
        //  "default": "default value associated to the variable (optional)",
        //  "preset": "boolean. If set to true, the UI will not generate an input (optional)",
        //  "select": "an array of possible values, will generate a select input (optional)"
        // }

        // LABELS
        /*
        {
          "labels": [
            { "name": "com.example.vendor", "value": "Acme" },
            { "name": "com.example.license", "value": "GPL" },
            { "name": "com.example.version", "value": "1.0" }
          ]
        }
         */

        template.setLabel(ContainerTemplateUtils.getLabelsFromApp(res));
        template.setEnv(ContainerTemplateUtils.getEnvVariablesFromApp(app));

        // Endpoints
        template.setPorts(ContainerTemplateUtils.getPortsFromApp(app));

        // StorageConfiguration
        template.setVolumes(ContainerTemplateUtils.getVolumesFromApp(app));

        return template;
    }


    /**
     * Create docker compliant image name.
     *
     * @param containerName The container name.
     * @return The compliant container name.
     */
    private String createDockerCompliantImageName(final String containerName) {
        // Replace Whitespaces with underlines
        final var newName = containerName.replaceAll("\\s", "_");
        final var allowedChars = "[a-zA-z0-9][a-zA-Z0-9_.-]";
        final var pattern = Pattern.compile("^" + allowedChars + "+$");
        final var matcher = pattern.matcher(newName);
        if (matcher.find()) {
            return newName;
        }
        return "";
    }

}

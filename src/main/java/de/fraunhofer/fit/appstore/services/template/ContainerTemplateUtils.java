/*
 * Copyright 2021 Fraunhofer Institute for Applied Information Technology
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
package de.fraunhofer.fit.appstore.services.template;

import de.fraunhofer.fit.appstore.model.portainer.Environment;
import de.fraunhofer.fit.appstore.model.portainer.Label;
import de.fraunhofer.fit.appstore.model.portainer.Template;
import de.fraunhofer.fit.appstore.model.portainer.Volume;
import io.dataspaceconnector.model.app.App;
import io.dataspaceconnector.model.representation.Representation;
import io.dataspaceconnector.model.resource.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class with utilities for the container template service.
 */
public final class ContainerTemplateUtils {

    private ContainerTemplateUtils() {
        // not used
    }

    /**
     * Set the title of a container template. Note: This is what is displayed when in the apps list
     * page. Punctuation is nice here and adds to the polish.
     *
     * @param app      The app.
     * @param rep      The representation.
     * @param template The container template.
     * @param res      The resource.
     */
    public static void setTemplateTitle(final App app, final Representation rep,
                                        final Template template, final Resource res) {
        if (!app.getTitle().isBlank()) {
            template.setTitle(app.getTitle());
        } else if (!rep.getTitle().isBlank()) {
            template.setTitle(rep.getTitle());
        } else if (!res.getTitle().isBlank()) {
            template.setTitle(res.getTitle());
        } else {
            template.setTitle("");
        }
    }

    /**
     * Set the description of a container template.
     *
     * @param app      The data app.
     * @param rep      The representation.
     * @param template The container template.
     * @param res      The app resource.
     */
    public static void setTemplateDescription(final App app, final Representation rep,
                                              final Template template, final Resource res) {
        if (!app.getDescription().isBlank()) {
            template.setDescription(app.getDescription());
        } else if (!rep.getDescription().isBlank()) {
            template.setDescription(rep.getDescription());
        } else if (!res.getDescription().isBlank()) {
            template.setDescription(res.getDescription());
        } else {
            template.setDescription("");
        }
    }

    /**
     * Extract volumes from app.
     *
     * @param app The data app.
     * @return A list of volumes.
     */
    public static ArrayList<Volume> getVolumesFromApp(final App app) {
        final var volumes = new ArrayList<Volume>();
        if (!app.getStorageConfig().isEmpty()) {
            final var volumesStr = app.getStorageConfig().replaceAll("\\s+", "").split(";");
            for (var volume : volumesStr) {
                var keyValue = volume.split(":");
                if (keyValue.length == 2) {
                    volumes.add(new Volume(keyValue[0], keyValue[1]));
                }
            }
        }

        return volumes;
    }

    /**
     * Extract ports from app.
     *
     * @param app The data app.
     * @return A list of ports.
     */
    public static ArrayList<Map<String, String>> getPortsFromApp(final App app) {
        final var portsTmp = new ArrayList<Map<String, String>>();
        // Map<String, String> ports = new HashMap<>();
        if (!app.getEndpoints().isEmpty()) {
            final var port = new HashMap<String, String>();
            for (var endpoint : app.getEndpoints()) {
                var portLabel = "";
                if (!endpoint.getType().toString().isBlank()) {
                    portLabel = endpoint.getType().toString();
                }
                // create String
                final var protocol = "tcp";
                final var portStr = String.format("%s:%s/%s", endpoint.getPort(),
                        endpoint.getPort(), protocol);

                if (!port.containsKey(portLabel)) {
                    port.put(portLabel, portStr);
                } else {
                    port.put(portLabel + "i", portStr);
                }
            }
            portsTmp.add(port);
        }

        return portsTmp;
    }

    /**
     * Extract env variables from app.
     *
     * @param app The data app.
     * @return A list of env variables.
     */
    public static ArrayList<Environment> getEnvVariablesFromApp(final App app) {
        final var environmentVariables = new ArrayList<Environment>();
        if (!app.getEnvironmentVariables().isBlank()) {
            final var envStr = app.getEnvironmentVariables().replaceAll("\\s+", "").split(";");
            for (final var env : envStr) {
                final var keyValue = env.split("=");
                if (keyValue.length == 2) {
                    environmentVariables.add(new Environment(keyValue[0], keyValue[0],
                            keyValue[1]));
                }
            }
        }
        return environmentVariables;
    }

    /**
     * Extract labels from app.
     *
     * @param res The app resource.
     * @return A list of labels.
     */
    public static ArrayList<Label> getLabelsFromApp(final Resource res) {
        final var labels = new ArrayList<Label>();
        if (!res.getLicense().toString().isBlank()) {
            labels.add(new Label("License", res.getLicense().toString()));
        }

        if (!res.getSovereign().toString().isBlank()) {
            labels.add(new Label("Author", res.getSovereign().toString()));
        }
        return labels;
    }
}

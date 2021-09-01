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
package de.fraunhofer.fit.appstore.model.portainer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.fraunhofer.fit.harbor.client.model.UserCreationReq;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.Map;

/**
 * A template contains information for creating a container.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Template implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    /**
     * The type.
     */
    @JsonProperty(required = true)
    private int type;

    /**
     * The title.
     */
    @JsonProperty(required = true)
    private String title;

    /**
     * The name.
     */
    @JsonProperty("name")
    private String name;

    /**
     * The description.
     */
    @JsonProperty(required = true)
    private String description;

    /**
     * The logo.
     */
    @JsonProperty("logo")
    private URI logo;

    /**
     * The image.
     */
    @JsonProperty(required = true)
    private String image;

    /**
     * The registry.
     */
    @JsonProperty("registry")
    private URI registry;

    /**
     * A list of categories.
     */
    @JsonProperty("categories")
    private ArrayList<String> categories;

    /**
     * The deployment platform.
     */
    @JsonProperty("platform")
    private String platform;

    /**
     * The restart policy.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String restartPolicy = "always";

    /**
     * A map containing ports.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ArrayList<Map<String, String>> ports;

    /**
     * A list of volumes.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ArrayList<Volume> volumes;

    /**
     * A list of commands.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ArrayList<String> command;

    /**
     * A list of environments.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("env")
    private ArrayList<Environment> env;

    /**
     * A list of labels.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ArrayList<Label> label;

    /**
     * A list of registry users.
     */
    @JsonProperty("registryUser")
    private UserCreationReq registryUser;

}

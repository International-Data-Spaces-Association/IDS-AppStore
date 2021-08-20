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
package de.fraunhofer.fit.appstore.model.registry.webhook;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * The repository.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Repository implements Serializable {

    /**
     * Default serial version uid.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The creation date.
     */
    @JsonProperty("date_created")
    private long dateCreated;

    /**
     * The name.
     */
    @JsonProperty("name")
    private String name;

    /**
     * The namespace.
     */
    @JsonProperty("namespace")
    private String namespace;

    /**
     * The full name.
     */
    @JsonProperty("repo_full_name")
    private String repoFullName;

    /**
     * The type.
     */
    @JsonProperty("repo_type")
    private String repoType;

}

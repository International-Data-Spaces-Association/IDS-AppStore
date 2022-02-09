/*
 * Copyright 2020 Fraunhofer Institute for Software and Systems Engineering
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
package io.dataspaceconnector.model.representation;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dataspaceconnector.model.base.RemoteObject;
import io.dataspaceconnector.model.named.NamedDescription;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.net.URI;

/**
 * Describes a representation. Use this for creating or updating a representation.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RepresentationDesc extends NamedDescription implements RemoteObject {

    /**
     * The representation id on provider side.
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private URI remoteId;

    /**
     * The media type expressed by this representation.
     */
    private String mediaType;

    /**
     * The language used by this representation.
     */
    private String language;

    /**
     * "Standard followed at representation level, i.e. it governs the serialization of an abstract
     * content like RDF/XML."
     */
    private String standard;

    /* AppStore Extension */

    /**
     * Data app runtime environment.
     */
    private String runtimeEnvironment;

    /**
     * Data app distribution service.
     */
    private URI distributionService;
}

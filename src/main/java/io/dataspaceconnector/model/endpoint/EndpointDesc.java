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
package io.dataspaceconnector.model.endpoint;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dataspaceconnector.model.named.NamedDescription;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.net.URI;

/**
 * Base class for describing endpoints.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class EndpointDesc extends NamedDescription {

    /**
     * The endpoint id on provider side.
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private URI remoteId;

    /**
     * The location information about the endpoint.
     */
    private URI location;

    /**
     * The media type expressed by this endpoint.
     */
    private String mediaType;

    /**
     * The port of the endpoint.
     */
    private Long port;

    /**
     * The protocol of the endpoint.
     */
    private String protocol;

    /**
     * The endpoint type.
     */
    private EndpointType type;
    /**
     * The documentation url.
     */
    private URI docs;
    /**
     * The information about the endpoint.
     */
    private String info;

    /**
     * The path of the endpoint.
     */
    private String path;
}

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
package io.dataspaceconnector.controller.resource.view.endpoint;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.dataspaceconnector.config.BaseType;
import io.dataspaceconnector.controller.resource.view.util.ViewConstants;
import io.dataspaceconnector.model.endpoint.EndpointType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.net.URI;
import java.time.ZonedDateTime;

/**
 * The view class for the endpoint proxy.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Relation(collectionRelation = BaseType.ENDPOINTS, itemRelation = "endpoint")
public class EndpointView extends RepresentationModel<EndpointView> {

    /**
     * The creation date.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ViewConstants.DATE_TIME_FORMAT)
    private ZonedDateTime creationDate;

    /**
     * The last modification date.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ViewConstants.DATE_TIME_FORMAT)
    private ZonedDateTime modificationDate;

    /**
     * The title of the endpoint.
     */
    private String title;

    /**
     * The description of the endpoint.
     */
    private String description;

    /**
     * The location information.
     */
    private String location;

    /**
     * The media type expressed by this endpoint.
     */
    private String mediaType;

    /**
     * The port of the endpoint.
     */
    private long port;

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

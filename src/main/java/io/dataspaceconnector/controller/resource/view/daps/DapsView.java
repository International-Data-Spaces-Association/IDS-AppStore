/*
 * Copyright 2022 sovity GmbH
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
package io.dataspaceconnector.controller.resource.view.daps;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.dataspaceconnector.config.BaseType;
import io.dataspaceconnector.controller.resource.view.util.ViewConstants;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.net.URI;
import java.time.ZonedDateTime;

/**
 * A DTO for controlled exposing of daps information in API responses.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Relation(collectionRelation = BaseType.DAPS, itemRelation = "daps")
public class DapsView extends RepresentationModel<DapsView> {

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
     * The url location of daps.
     */
    private URI location;

    /**
     * The title of the daps.
     */
    private String title;

    /**
     * The description of the daps.
     */
    private String description;

    /**
     * Whether this daps is whitelisted.
     */
    private Boolean whitelisted;
}

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
package io.dataspaceconnector.controller.resource.view.app;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.dataspaceconnector.common.ids.policy.PolicyPattern;
import io.dataspaceconnector.config.BaseType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

/**
 * A DTO for controlled exposing of catalog information in API responses.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true) // TODO APPSTORE add app attributes
@Relation(collectionRelation = BaseType.APPS, itemRelation = "catalog")
public class AppView extends RepresentationModel<AppView> {
    /**
     * The creation date.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private ZonedDateTime creationDate;

    /**
     * The last modification date.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private ZonedDateTime modificationDate;

    /**
     * The title of the data app.
     */
    private String title;

    /**
     * The description of the data app.
     */
    private String description;

    /**
     * The documentation of the data app.
     */
    private String docs;

    /**
     * The environment variables of the data app.
     */
    private String environmentVariables;

    /**
     * The storage configuration of the data app.
     */
    private String storageConfig;

    /**
     * List of supported usage policies.
     */
    private List<PolicyPattern> supportedUsagePolicies;

    /**
     * Additional properties.
     */
    private Map<String, String> additional;
}

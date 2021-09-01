/*
 * Copyright 2020 Fraunhofer Institute for Software and Systems Engineering
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
package io.dataspaceconnector.model.endpoint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.dataspaceconnector.model.app.App;
import io.dataspaceconnector.model.base.RemoteService;
import io.dataspaceconnector.model.named.NamedEntity;
import io.dataspaceconnector.model.util.UriConverter;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.net.URI;
import java.util.List;

import static io.dataspaceconnector.model.config.DatabaseConstants.URI_COLUMN_LENGTH;

/**
 * Entity which manages the endpoints.
 */
@javax.persistence.Entity
@Table(name = "endpoint")
@SQLDelete(sql = "UPDATE endpoint SET deleted=true WHERE id=?")
@Where(clause = "deleted = false")
@Getter
@Setter(AccessLevel.PACKAGE)
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
public class Endpoint extends NamedEntity implements RemoteService {

    /**
     * Serial version uid.
     **/
    private static final long serialVersionUID = 1L;

    /**
     * The endpoint id on connector side.
     */
    @Convert(converter = UriConverter.class)
    @Column(length = URI_COLUMN_LENGTH)
    private URI remoteId;

    /**+
     * The access url of the endpoint.
     */
    @Convert(converter = UriConverter.class)
    @Column(length = URI_COLUMN_LENGTH)
    private URI location; // mapped to accessURL

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
    @Enumerated(EnumType.STRING)
    private EndpointType type;

    /**
     * The documentation for the endpoint.
     */
    @Convert(converter = UriConverter.class)
    @Column(length = URI_COLUMN_LENGTH)
    private URI docs;

    /**
     * The information about the endpoint.
     */
    private String info;

    /**
     * The type information.
     */
    private String path;

    /**
     * A list of related apps.
     */
    @JsonIgnore
    @ManyToMany(mappedBy = "endpoints")
    private List<App> apps;
}

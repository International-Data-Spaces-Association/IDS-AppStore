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
package io.dataspaceconnector.model.app;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.dataspaceconnector.common.ids.policy.PolicyPattern;
import io.dataspaceconnector.model.base.RemoteObject;
import io.dataspaceconnector.model.endpoint.Endpoint;
import io.dataspaceconnector.model.named.NamedEntity;
import io.dataspaceconnector.model.representation.Representation;
import io.dataspaceconnector.model.util.UriConverter;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.net.URI;
import java.util.List;

import static io.dataspaceconnector.model.config.DatabaseConstants.URI_COLUMN_LENGTH;

/**
 * A resource describes offered or requested data.
 */
@javax.persistence.Entity
@Table(name = "app")
@SQLDelete(sql = "UPDATE app SET deleted=true WHERE id=?")
@Where(clause = "deleted = false")
@Getter
@Setter(AccessLevel.PACKAGE)
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
@Indexed
public class App extends NamedEntity implements RemoteObject {

    /**
     * Serial version uid.
     **/
    private static final long serialVersionUID = 1L;

    /**
     * The app id on connector side.
     */
    @Convert(converter = UriConverter.class)
    @Column(length = URI_COLUMN_LENGTH)
    private URI remoteId;

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
    @Enumerated(EnumType.STRING)
    @ElementCollection
    private List<PolicyPattern> supportedUsagePolicies;

    /**
     * The endpoints of the data app.
     */
    @ManyToMany
    private List<Endpoint> endpoints;

    /**
     * The representation of the data app.
     */
    @JsonIgnore
    @ManyToMany(mappedBy = "dataApps")
    private List<Representation> representations;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SecurityScan Extensions                                                                    //
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Security scanner name.
     */
    private String securityScannerName;

    /**
     * Security scanner vendor.
     */
    private String securityScannerVendor;

    /**
     * Security scanner version.
     */
    private String securityScannerVersion;

    /**
     * Security scanner complete percent.
     */
    private long securityScannerCompletePercent;

    /**
     * Security scanner total issues.
     */
    private long securityScannerIssuesTotal;

    /**
     * Security scanner fixable issues.
     */
    private long securityScannerIssuesFixable;

    /**
     * Security scanner log issues.
     */
    private long securityScannerIssuesLow;

    /**
     * Security scanner medium issues.
     */
    private long securityScannerIssuesMedium;

    /**
     * Security scanner high issues.
     */
    private long securityScannerIssuesHigh;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Registry Extensions                                                                        //
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * The repository name.
     */
    private String repositoryName;

    /**
     * The repository name space.
     */
    private String repositoryNameSpace;

    /**
     * The repository digest.
     */
    private String repositoryDigest;

}

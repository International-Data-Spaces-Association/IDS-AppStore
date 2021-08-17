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

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dataspaceconnector.common.ids.policy.PolicyPattern;
import io.dataspaceconnector.model.named.NamedDescription;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.ElementCollection;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.net.URI;
import java.util.List;

/**
 * Base class for describing resources.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AppDesc extends NamedDescription {

    /**
     * The resource id on provider side.
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private URI remoteId;

    // TODO APPSTORE copy app attributes --> Done!

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

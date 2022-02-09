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

import io.dataspaceconnector.common.ids.policy.PolicyPattern;
import io.dataspaceconnector.model.named.NamedDescription;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * Describes a data app. Use this structure to create or update a data app.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class AppDesc extends NamedDescription {

    /***********************************************************************************************
     * Artifact attributes                                                                         *
     ***********************************************************************************************

    /**
     * The artifact id on provider side.
     */
    private URI remoteId;

    /**
     * The provider's address for artifact request messages.
     */
    private URI remoteAddress;

    /**
     * Some value for storing data locally.
     */
    private String value;

    /***********************************************************************************************
     * App attributes                                                                              *
     ***********************************************************************************************

    /**
     * Text documentation of the data app.
     */
    private String docs;

    /**
     * Environment variables of the data app.
     */
    private String envVariables;

    /**
     * Storage configuration of the data app (e.g. path in the file system or volume name).
     */
    private String storageConfig;

    /**
     * Usage policy patterns supported by the data app.
     */
    private List<PolicyPattern> supportedPolicies;

    /***********************************************************************************************
     * Resource attributes                                                                         *
     ***********************************************************************************************

    /**
     * The keywords of the resource.
     */
    private List<String> keywords;

    /**
     * The publisher of the resource.
     */
    private URI publisher;

    /**
     * The owner of the resource.
     */
    private URI sovereign;

    /**
     * The language of the resource.
     */
    private String language;

    /**
     * The license of the resource.
     */
    private URI license;

    /**
     * The endpoint of the resource.
     */
    private URI endpointDocumentation;

    /***********************************************************************************************
     * Representation attributes                                                                   *
     ***********************************************************************************************

    /**
     * Distribution service, where the represented app can be downloaded.
     */
    private URI distributionService;

    /**
     * "Runtime environment of a data app, e.g., software (or hardware) required to run the app.
     */
    private String runtimeEnvironment;

    /**
     * Additional properties.
     */
    private Map<String, String> additional;

    /* AppStore Extension */
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

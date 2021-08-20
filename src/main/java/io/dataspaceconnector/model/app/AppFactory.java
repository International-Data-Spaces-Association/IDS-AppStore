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
package io.dataspaceconnector.model.app;

import de.fraunhofer.iais.eis.util.Util;
import io.dataspaceconnector.common.ids.policy.PolicyPattern;
import io.dataspaceconnector.model.named.AbstractNamedFactory;
import io.dataspaceconnector.model.util.FactoryUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Base class for creating and updating resources.
 */
@Component
public class AppFactory extends AbstractNamedFactory<App, AppDesc> {

    /**
     * The default remote id assigned to all apps.
     */
    public static final URI DEFAULT_REMOTE_ID = URI.create("");

    /**
     * The default storageConfiguration assigned to all apps.
     */
    public static final String DEFAULT_STORAGE_CONFIGURATION = "/data:/data";

    /**
     * The default environmentVariables assigned to all apps.
     */
    public static final String DEFAULT_ENVIRONMENT_VARIABLES = "";

    /**
     * The default documentation assigned to all apps.
     */
    public static final String DEFAULT_DOCS = "";

    /**
     * The default supportedUsagePolicies assigned to all apps.
     */
    private static final List<PolicyPattern> DEFAULT_SUPPORTED_USAGE_POLICIES
            = Util.asList(PolicyPattern.PROVIDE_ACCESS);

    /**
     * The default number of high issues.
     */
    private static final Integer DEFAULT_SECURITY_SCANNER_ISSUES_HIGH = 0;

    /**
     * The default number of medium issues.
     */
    private static final Integer DEFAULT_SECURITY_SCANNER_ISSUES_MEDIUM = 0;

    /**
     * The default number of low issues.
     */
    private static final Integer DEFAULT_SECURITY_SCANNER_ISSUES_LOW = 0;

    /**
     * The default number of fixable issues.
     */
    private static final Integer DEFAULT_SECURITY_SCANNER_ISSUES_FIXABLE = 0;

    /**
     * The default number of total issues.
     */
    private static final Integer DEFAULT_SECURITY_SCANNER_ISSUES_TOTAL = 0;

    /**
     * The default number of complete percent.
     */
    private static final Integer DEFAULT_SECURITY_SCANNER_COMPLETE_PERCENT = 0;

    /**
     * The default security scanner version.
     */
    private static final String DEFAULT_SECURITY_SCANNER_VERSION = "";

    /**
     * The default security scanner vendor.
     */
    private static final String DEFAULT_SECURITY_SCANNER_VENDOR = "";

    /**
     * The default security scanner name.
     */
    private static final String DEFAULT_SECURITY_SCANNER_NAME = "";

    /**
     * The default repository digest.
     */
    private static final String DEFAULT_REPOSITORY_DIGEST = "";

    /**
     * The default repository namespace.
     */
    @Value("${registry.url}")
    private String defaultRepositoryNamespace = "";

    /**
     * The default repository name.
     */
    private static final String DEFAULT_REPOSITORY_NAME = "";

    /**
     * Create a new resource.
     *
     * @param desc The description of the new resource.
     * @return The new resource.
     * @throws IllegalArgumentException if desc is null.
     */
    @Override
    protected App initializeEntity(final AppDesc desc) {
        final var app = new App();
        app.setEndpoints(new ArrayList<>());
        app.setRepresentations(new ArrayList<>());

        return app;
    }

    /**
     * Update an app.
     *
     * @param app  The app to be updated.
     * @param desc The new app description.
     * @return True if the app has been modified.
     * @throws IllegalArgumentException if any of the parameters is null.
     */
    @Override
    protected boolean updateInternal(final App app, final AppDesc desc) {
        final var hasRemoteIdUpdated = updateRemoteId(app, desc.getRemoteId());
        final var hasDocumentationUpdated = updateDocs(app, desc.getDocs());
        final var hasEnvironmentVariablesUpdated
                = updateEnvironmentVariables(app, desc.getEnvironmentVariables());
        final var hasStorageConfigurationUpdated
                = updateStorageConfiguration(app, desc.getStorageConfig());
        final var hasSupportedUsagePoliciesUpdated
                = updateSupportedUsagePolicies(app, desc.getSupportedUsagePolicies());
        final var hasSecurityScannerNameUpdated
                = updateSecurityScannerName(app, desc.getSecurityScannerName());
        final var hasSecurityScannerVendorUpdated
                = updateSecurityScannerVendor(app, desc.getSecurityScannerVendor());
        final var hasSecurityScannerVersionUpdated
                = updateSecurityScannerVersion(app, desc.getSecurityScannerVersion());
        final var hasSecurityScannerCompletePercentUpdated
                = updateSecurityScannerCompletePercent(app,
                desc.getSecurityScannerCompletePercent());
        final var hasSecurityScannerIssuesTotalUpdated
                = updateSecurityScannerIssuesTotal(app, desc.getSecurityScannerIssuesTotal());
        final var hasSecurityScannerIssuesFixableUpdated
                = updateSecurityScannerIssuesFixable(app, desc.getSecurityScannerIssuesFixable());
        final var hasSecurityScannerIssuesLowUpdated
                = updateSecurityScannerIssuesLow(app, desc.getSecurityScannerIssuesLow());
        final var hasSecurityScannerIssuesMediumUpdated
                = updateSecurityScannerIssuesMedium(app, desc.getSecurityScannerIssuesMedium());
        final var hasSecurityScannerIssuesHighUpdated
                = updateSecurityScannerIssuesHigh(app, desc.getSecurityScannerIssuesHigh());
        final var hasRepositoryNameUpdated = updateRepositoryName(app, desc.getRepositoryName());
        final var hasRepositoryNameSpaceUpdated
                = updateRepositoryNameSpace(app, desc.getRepositoryNameSpace());
        final var hasRepositoryDigestUpdated
                = updateRepositoryDigest(app, desc.getRepositoryDigest());

        return hasRemoteIdUpdated || hasDocumentationUpdated
                || hasEnvironmentVariablesUpdated || hasStorageConfigurationUpdated
                || hasSupportedUsagePoliciesUpdated
                || hasSecurityScannerNameUpdated
                || hasSecurityScannerVendorUpdated
                || hasSecurityScannerVersionUpdated
                || hasSecurityScannerCompletePercentUpdated
                || hasSecurityScannerIssuesTotalUpdated
                || hasSecurityScannerIssuesFixableUpdated
                || hasSecurityScannerIssuesLowUpdated
                || hasSecurityScannerIssuesMediumUpdated
                || hasSecurityScannerIssuesHighUpdated
                || hasRepositoryNameUpdated
                || hasRepositoryNameSpaceUpdated
                || hasRepositoryDigestUpdated;
    }


    /**
     * Update an app's documentation.
     *
     * @param app  The app.
     * @param docs The new documentation.
     * @return true if the app's documentation has been modified.
     */
    private boolean updateDocs(final App app, final String docs) {
        final var newDocs =
                FactoryUtils.updateString(app.getDocs(), docs, DEFAULT_DOCS);
        newDocs.ifPresent(app::setDocs);

        return newDocs.isPresent();
    }

    /**
     * Update an app's environmentVariables.
     *
     * @param app                  The app.
     * @param environmentVariables The new environmentVariables.
     * @return true if the app's environmentVariables has been modified.
     */
    private boolean updateEnvironmentVariables(final App app, final String environmentVariables) {
        final var newEnvironmentVariables =
                FactoryUtils.updateString(app.getEnvironmentVariables(),
                        environmentVariables, DEFAULT_ENVIRONMENT_VARIABLES);
        newEnvironmentVariables.ifPresent(app::setEnvironmentVariables);

        return newEnvironmentVariables.isPresent();
    }

    /**
     * Update an app's storageConfiguration.
     *
     * @param app                  The app.
     * @param storageConfiguration The new storageConfiguration.
     * @return true if the app's storageConfiguration has been modified.
     */
    private boolean updateStorageConfiguration(final App app, final String storageConfiguration) {
        final var newStorageConfiguration =
                FactoryUtils.updateString(app.getStorageConfig(), storageConfiguration,
                        DEFAULT_STORAGE_CONFIGURATION);
        newStorageConfiguration.ifPresent(app::setStorageConfig);

        return newStorageConfiguration.isPresent();
    }

    /**
     * Update an app's supportedUsagePolicies.
     *
     * @param app                    The app.
     * @param supportedUsagePolicies The new supportedUsagePolicies.
     * @return true if the app's supportedUsagePolicies has been modified.
     */
    private boolean updateSupportedUsagePolicies(final App app,
                                                 final List<PolicyPattern> supportedUsagePolicies) {
        final var newSupportedUsagePolicies =
                FactoryUtils.updateEnumList(app.getSupportedUsagePolicies(),
                        supportedUsagePolicies, DEFAULT_SUPPORTED_USAGE_POLICIES);
        newSupportedUsagePolicies.ifPresent(app::setSupportedUsagePolicies);
        return newSupportedUsagePolicies.isPresent();
    }

    private boolean updateSecurityScannerName(final App app, final String securityScannerName) {
        final var newSecurityScannerName =
                FactoryUtils.updateString(app.getSecurityScannerName(), securityScannerName,
                        DEFAULT_SECURITY_SCANNER_NAME);
        newSecurityScannerName.ifPresent(app::setSecurityScannerName);

        return newSecurityScannerName.isPresent();
    }

    private boolean updateSecurityScannerVendor(final App app, final String securityScannerVendor) {
        final var newSecurityScannerVendor =
                FactoryUtils.updateString(app.getSecurityScannerVendor(), securityScannerVendor,
                        DEFAULT_SECURITY_SCANNER_VENDOR);
        newSecurityScannerVendor.ifPresent(app::setSecurityScannerVendor);

        return newSecurityScannerVendor.isPresent();
    }

    private boolean updateSecurityScannerVersion(final App app,
                                                 final String securityScannerVersion) {
        final var newSecurityScannerVersion =
                FactoryUtils.updateString(app.getSecurityScannerVersion(), securityScannerVersion,
                        DEFAULT_SECURITY_SCANNER_VERSION);
        newSecurityScannerVersion.ifPresent(app::setSecurityScannerVersion);

        return newSecurityScannerVersion.isPresent();
    }

    private boolean updateSecurityScannerCompletePercent(final App app, final Long percent) {
        final var tmp = percent == 0 ? DEFAULT_SECURITY_SCANNER_COMPLETE_PERCENT : percent;

        final var newValue
                = FactoryUtils.updateNumber(app.getSecurityScannerCompletePercent(), tmp);
        if (newValue == app.getSecurityScannerCompletePercent()) {
            return false;
        }
        app.setSecurityScannerCompletePercent(newValue);
        return true;
    }

    private boolean updateSecurityScannerIssuesTotal(final App app, final Long number) {
        final var tmp = number == null ? DEFAULT_SECURITY_SCANNER_ISSUES_TOTAL : number;

        final var newValue = FactoryUtils.updateNumber(app.getSecurityScannerIssuesTotal(), tmp);
        if (newValue == app.getSecurityScannerIssuesTotal()) {
            return false;
        }
        app.setSecurityScannerIssuesTotal(newValue);
        return true;
    }

    private boolean updateSecurityScannerIssuesFixable(final App app, final Long number) {
        final var tmp = number == null ? DEFAULT_SECURITY_SCANNER_ISSUES_FIXABLE : number;

        final var newValue = FactoryUtils.updateNumber(app.getSecurityScannerIssuesFixable(), tmp);
        if (newValue == app.getSecurityScannerIssuesFixable()) {
            return false;
        }
        app.setSecurityScannerIssuesFixable(newValue);
        return true;
    }

    private boolean updateSecurityScannerIssuesLow(final App app, final Long number) {
        final var tmp = number == null ? DEFAULT_SECURITY_SCANNER_ISSUES_LOW : number;

        final var newValue = FactoryUtils.updateNumber(app.getSecurityScannerIssuesLow(), tmp);
        if (newValue == app.getSecurityScannerIssuesLow()) {
            return false;
        }
        app.setSecurityScannerIssuesLow(newValue);
        return true;
    }

    private boolean updateSecurityScannerIssuesMedium(final App app, final Long number) {
        final var tmp = number == null ? DEFAULT_SECURITY_SCANNER_ISSUES_MEDIUM : number;

        final var newValue = FactoryUtils.updateNumber(app.getSecurityScannerIssuesMedium(), tmp);
        if (newValue == app.getSecurityScannerIssuesMedium()) {
            return false;
        }
        app.setSecurityScannerIssuesMedium(newValue);
        return true;
    }

    private boolean updateSecurityScannerIssuesHigh(final App app, final Long number) {
        final var tmp = number == null ? DEFAULT_SECURITY_SCANNER_ISSUES_HIGH : number;

        final var newValue = FactoryUtils.updateNumber(app.getSecurityScannerIssuesHigh(), tmp);
        if (newValue == app.getSecurityScannerIssuesHigh()) {
            return false;
        }
        app.setSecurityScannerIssuesHigh(newValue);
        return true;
    }

    private boolean updateRepositoryName(final App app, final String value) {
        final var newValue = FactoryUtils.updateString(app.getRepositoryName(),
                value, DEFAULT_REPOSITORY_NAME);
        newValue.ifPresent(app::setRepositoryName);

        return newValue.isPresent();
    }

    private boolean updateRepositoryNameSpace(final App app, final String value) {
        final var newValue = FactoryUtils.updateString(app.getRepositoryNameSpace(),
                value, defaultRepositoryNamespace);
        newValue.ifPresent(app::setRepositoryNameSpace);

        return newValue.isPresent();
    }

    private boolean updateRepositoryDigest(final App app, final String digest) {
        final var newValue = FactoryUtils.updateString(app.getRepositoryDigest(), digest,
                DEFAULT_REPOSITORY_DIGEST);
        newValue.ifPresent(app::setRepositoryDigest);

        return newValue.isPresent();
    }


    private boolean updateRemoteId(final App app, final URI remoteId) {
        final var newUri = FactoryUtils.updateUri(app.getRemoteId(), remoteId,
                DEFAULT_REMOTE_ID);
        newUri.ifPresent(app::setRemoteId);

        return newUri.isPresent();
    }

    /**
     * Get default repository namespace.
     *
     * @return The default repository namespace.
     */
    public String getDefaultRepositoryNamespace() {
        return defaultRepositoryNamespace;
    }

    /**
     * Set default repository namespace.
     *
     * @param namespace The default repository namespace.
     */
    public void setDefaultRepositoryNamespace(final String namespace) {
        this.defaultRepositoryNamespace = defaultRepositoryNamespace;
    }
}

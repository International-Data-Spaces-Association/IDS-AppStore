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
package io.dataspaceconnector.common.ids.mapping;

import de.fraunhofer.iais.eis.AppEndpointType;
import de.fraunhofer.iais.eis.BaseConnectorBuilder;
import de.fraunhofer.iais.eis.BasicAuthentication;
import de.fraunhofer.iais.eis.BasicAuthenticationBuilder;
import de.fraunhofer.iais.eis.Connector;
import de.fraunhofer.iais.eis.ConnectorDeployMode;
import de.fraunhofer.iais.eis.ConnectorEndpointBuilder;
import de.fraunhofer.iais.eis.ConnectorStatus;
import de.fraunhofer.iais.eis.Language;
import de.fraunhofer.iais.eis.LogLevel;
import de.fraunhofer.iais.eis.PaymentModality;
import de.fraunhofer.iais.eis.Proxy;
import de.fraunhofer.iais.eis.ProxyBuilder;
import de.fraunhofer.iais.eis.SecurityProfile;
import de.fraunhofer.iais.eis.UsagePolicyClass;
import de.fraunhofer.iais.eis.util.TypedLiteral;
import de.fraunhofer.iais.eis.util.Util;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.dataspaceconnector.common.ids.policy.PolicyPattern;
import io.dataspaceconnector.model.auth.BasicAuth;
import io.dataspaceconnector.model.configuration.Configuration;
import io.dataspaceconnector.model.configuration.DeployMode;
import io.dataspaceconnector.model.resource.PaymentMethod;
import lombok.SneakyThrows;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.net.URI;
import java.text.Normalizer;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.TimeZone;
import java.util.stream.Collectors;

/**
 * Maps internal entities to ids objects.
 */
public final class ToIdsObjectMapper {

    /**
     * Default constructor.
     */
    private ToIdsObjectMapper() {
        // not used
    }

    /**
     * Get list of keywords as ids list of typed literals.
     *
     * @param keywords List of keywords.
     * @param language The language.
     * @return List of typed literal.
     */
    public static List<TypedLiteral> getKeywordsAsTypedLiteral(final List<String> keywords,
                                                               final String language) {
        final var idsKeywords = new ArrayList<TypedLiteral>();
        for (final var keyword : keywords) {
            idsKeywords.add(new TypedLiteral(keyword, language));
        }

        return idsKeywords;
    }

    /**
     * Convert string to ids language.
     *
     * @param language The language as string.
     * @return The ids language object.
     */
    @SuppressFBWarnings("IMPROPER_UNICODE")
    public static Language getLanguage(final String language) {
        for (final var idsLanguage : Language.values()) {
            if (Normalizer.normalize(language.toLowerCase(Locale.ROOT),
                    Normalizer.Form.NFC).equals(Normalizer.normalize(
                    idsLanguage.name().toLowerCase(Locale.ROOT), Normalizer.Form.NFC))) {
                return idsLanguage;
            }
        }

        return Language.EN;
    }

    /**
     * Converts a date to XMLGregorianCalendar format.
     *
     * @param date the date object.
     * @return the XMLGregorianCalendar object or null.
     */
    @SneakyThrows
    public static XMLGregorianCalendar getGregorianOf(final ZonedDateTime date) {
        final var calendar = GregorianCalendar.from(date);
        calendar.setTimeZone(TimeZone.getTimeZone(ZoneId.ofOffset("", ZoneOffset.UTC)));
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
    }

    /**
     * Get security profile from string.
     *
     * @param input The input value.
     * @return A security profile, if the value matches the provided enums.
     */
    public static Optional<SecurityProfile> getSecurityProfile(final String input) {
        switch (input) {
            case "idsc:BASE_SECURITY_PROFILE":
            case "BASE_SECURITY_PROFILE":
            case "idsc:BASE_CONNECTOR_SECURITY_PROFILE":
                return Optional.of(SecurityProfile.BASE_SECURITY_PROFILE);
            case "idsc:TRUST_SECURITY_PROFILE":
            case "TRUST_SECURITY_PROFILE":
            case "idsc:TRUST_CONNECTOR_SECURITY_PROFILE":
                return Optional.of(SecurityProfile.TRUST_SECURITY_PROFILE);
            case "idsc:TRUST_PLUS_SECURITY_PROFILE":
            case "TRUST_PLUS_SECURITY_PROFILE":
            case "idsc:TRUST_PLUS_CONNECTOR_SECURITY_PROFILE":
                return Optional.of(SecurityProfile.TRUST_PLUS_SECURITY_PROFILE);
            default:
                return Optional.empty();
        }
    }

    /**
     * Get ids deploy mode from dsc deploy mode.
     *
     * @param deployMode The internal deploy mode.
     * @return The ids deploy mode.
     */
    public static ConnectorDeployMode getConnectorDeployMode(final DeployMode deployMode) {
        return deployMode == DeployMode.TEST ? ConnectorDeployMode.TEST_DEPLOYMENT
                : ConnectorDeployMode.PRODUCTIVE_DEPLOYMENT;
    }

    /**
     * Get ids log level from dsc log level.
     *
     * @param logLevel The internal log level.
     * @return The ids log level.
     */
    public static LogLevel getLogLevel(
            final io.dataspaceconnector.model.configuration.LogLevel logLevel) {
        switch (logLevel) {
            // TODO infomodel has less log levels than DSC, info will get lost.
            case INFO:
            case WARN:
            case ERROR:
            case TRACE:
                return LogLevel.MINIMAL_LOGGING;
            case DEBUG:
                return LogLevel.DEBUG_LEVEL_LOGGING;
            default:
                return LogLevel.NO_LOGGING;
        }
    }

    /**
     * Get the ids connector status from dsc connector status.
     *
     * @param status The internal connector status.
     * @return The ids connector status.
     */
    public static ConnectorStatus getConnectorStatus(
            final io.dataspaceconnector.model.configuration.ConnectorStatus status) {
        switch (status) {
            case ONLINE:
                return ConnectorStatus.CONNECTOR_ONLINE;
            case OFFLINE:
                return ConnectorStatus.CONNECTOR_OFFLINE;
            default:
                return ConnectorStatus.CONNECTOR_BADLY_CONFIGURED;
        }
    }

    /**
     * Get ids proxy from dsc proxy.
     *
     * @param proxy The internal proxy.
     * @return The ids proxy.
     */
    public static Proxy getProxy(final io.dataspaceconnector.model.proxy.Proxy proxy) {
        // TODO auth from DSC has ID field, not available in InfoModel. Create auth build service.
        return new ProxyBuilder()._noProxy_(proxy.getExclusions()
                .stream()
                .map(URI::create)
                .collect(Collectors.toList()))
                ._proxyAuthentication_(proxy.getAuthentication() == null
                        ? null
                        : getBasicAuthHeader(proxy.getAuthentication()))
                ._proxyURI_(proxy.getLocation())
                .build();
    }

    private static BasicAuthentication getBasicAuthHeader(final BasicAuth auth) {
        return new BasicAuthenticationBuilder()
                ._authPassword_(auth.getPassword())
                ._authUsername_(auth.getUsername())
                .build();
    }

    /**
     * Fill ids connector with config properties.
     *
     * @param config The internal configuration model.
     * @return The filled ids connector.
     */
    public static Connector getConnectorFromConfiguration(final Configuration config) {
        return new BaseConnectorBuilder(config.getConnectorId())
                ._title_(Util.asList(new TypedLiteral(config.getTitle())))
                ._description_(Util.asList(new TypedLiteral(config.getDescription())))
                ._curator_(config.getCurator())
                ._maintainer_(config.getMaintainer())
                ._securityProfile_(config.getSecurityProfile() == null
                        ? SecurityProfile.BASE_SECURITY_PROFILE
                        : getSecurityProfile(config.getSecurityProfile()))
                ._hasDefaultEndpoint_(new ConnectorEndpointBuilder()
                        ._accessURL_(config.getDefaultEndpoint())
                        .build())
                ._outboundModelVersion_(config.getOutboundModelVersion())
                ._inboundModelVersion_(config.getInboundModelVersion())
                ._version_(config.getVersion())
                .build();
    }

    /**
     * Get ids security profile from dsc security profile.
     *
     * @param securityProfile The internal security profile.
     * @return The ids security profile.
     */
    private static SecurityProfile getSecurityProfile(
            final io.dataspaceconnector.model.configuration.SecurityProfile securityProfile) {
        switch (securityProfile) {
            case TRUST_SECURITY:
                return SecurityProfile.TRUST_SECURITY_PROFILE;
            case TRUST_PLUS_SECURITY:
                return SecurityProfile.TRUST_PLUS_SECURITY_PROFILE;
            default:
                return SecurityProfile.BASE_SECURITY_PROFILE;
        }
    }

    /**
     * Get ids payment modality from dsc payment method.
     *
     * @param paymentMethod The payment method.
     * @return The ids payment modality.
     */
    public static PaymentModality getPaymentModality(final PaymentMethod paymentMethod) {

        switch (paymentMethod) {
            case FREE:
                return PaymentModality.FREE;
            case NEGOTIATION_BASIS:
                return PaymentModality.NEGOTIATION_BASIS;
            case FIXED_PRICE:
                return PaymentModality.FIXED_PRICE;
            default:
                return null;
        }
    }

    /**
     * Get ids AppEndpointType from dsc endpoint type.
     *
     * @param endpointType The dsc endpointType
     * @return The ids AppEndpointType
     */
    public static AppEndpointType getAppEndpointType(
            final io.dataspaceconnector.model.endpoint.EndpointType endpointType) {
        switch (endpointType) {
            case CONFIG_ENDPOINT:
                return AppEndpointType.CONFIG_ENDPOINT;
            case STATUS_ENDPOINT:
                return AppEndpointType.STATUS_ENDPOINT;
            case OUTPUT_ENDPOINT:
                return AppEndpointType.OUTPUT_ENDPOINT;
            case USAGE_POLICY_ENDPOINT:
                return AppEndpointType.USAGE_POLICY_ENDPOINT;
            case INPUT_ENDPOINT:
            default:
                return AppEndpointType.INPUT_ENDPOINT;
        }
    }

    // FIXME: Did all cases match the right values?
    private static UsagePolicyClass getUsagePolicyClass(final PolicyPattern policyPattern) {
        switch (policyPattern) {
            case PROHIBIT_ACCESS:
                return UsagePolicyClass.PURPOSE_RESTRICTED_DATA_USAGE;
            case N_TIMES_USAGE:
                return UsagePolicyClass.RESTRICTED_NUMBER_OF_USAGES;
            case DURATION_USAGE:
                return UsagePolicyClass.DURATION_RESTRICTED_DATA_USAGE;
            case USAGE_DURING_INTERVAL:
                return UsagePolicyClass.INTERVAL_RESTRICTED_DATA_USAGE;
            case USAGE_UNTIL_DELETION:
                return UsagePolicyClass.USE_DATA_AND_DELETE_AFTER;
            case USAGE_LOGGING:
                return UsagePolicyClass.LOCAL_LOGGING;
            case USAGE_NOTIFICATION:
                return UsagePolicyClass.REMOTE_NOTIFICATION;
            case CONNECTOR_RESTRICTED_USAGE:
                return UsagePolicyClass.CONNECTOR_RESTRICTED_DATA_USAGE;
            case SECURITY_PROFILE_RESTRICTED_USAGE:
                return UsagePolicyClass.SECURITY_LEVEL_RESTRICTED_POLICY;
            default:
                return UsagePolicyClass.ALLOW_DATA_USAGE;
        }
    }

    /**
     * Converts a List of dsc policy patterns to ids usage policy class.
     *
     * @param policyPatterns List of policy patterns.
     * @return A list of usage policy classes.
     */
    public static List<UsagePolicyClass> getListOfUsagePolicyClasses(
            final List<PolicyPattern> policyPatterns) {

        final var policyClasses = new ArrayList<UsagePolicyClass>();
        policyPatterns.forEach(pattern -> {
            policyClasses.add(getUsagePolicyClass(pattern));
        });
        return policyClasses;
    }
}

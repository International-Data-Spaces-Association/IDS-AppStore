/*
 * Copyright 2020-2022 Fraunhofer Institute for Software and Systems Engineering
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
package io.dataspaceconnector.service.usagecontrol;

import de.fraunhofer.iais.eis.ContractAgreement;
import de.fraunhofer.iais.eis.Permission;
import de.fraunhofer.iais.eis.Rule;
import de.fraunhofer.ids.messaging.core.config.util.ConnectorFingerprintProvider;
import io.dataspaceconnector.common.exception.PolicyExecutionException;
import io.dataspaceconnector.common.ids.ConnectorService;
import io.dataspaceconnector.common.ids.mapping.RdfConverter;
import io.dataspaceconnector.common.ids.message.ClearingHouseService;
import io.dataspaceconnector.common.ids.policy.RuleUtils;
import io.dataspaceconnector.service.message.builder.type.NotificationService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.HashMap;

/**
 * Executes policy conditions. Refers to the ids policy enforcement point (PEP).
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class PolicyExecutionService {

    /**
     * Service for ids notification messages.
     */
    private final @NonNull NotificationService notificationSvc;

    /**
     * Service for sending messages to the clearing house.
     */
    private final @NonNull ClearingHouseService clearingHouseSvc;

    /**
     * Service for the current connector configuration.
     */
    private final @NonNull ConnectorService connectorSvc;

    /**
     * Send contract agreement to clearing house.
     *
     * @param agreement The ids contract agreement.
     * @param claims The claims of the requesting connector.
     */
    public void sendAgreement(final ContractAgreement agreement, final Jws<Claims> claims) {
        try {
            // Check if claims of requesting connector are present.
            if (claims == null || claims.getBody() == null) {
                throw new IllegalArgumentException("Claims of requesting connector missing. "
                        + "Cannot create process at Clearing House.");
            }

            // Check if fingerprints of both connectors are present.
            var consumerFingerprint = claims.getBody().getSubject();
            var providerFingerprint = ConnectorFingerprintProvider.fingerprint;
            if (consumerFingerprint == null || providerFingerprint.isEmpty()) {
                throw new IllegalArgumentException("Connector fingerprint missing. "
                        + "Cannot create process at Clearing House.");
            }

            // Create a process with the agreement's UUID at the Clearing House
            clearingHouseSvc.createProcessAtClearingHouse(agreement, providerFingerprint.get(),
                    consumerFingerprint);

            // Log the agreement under the previously created process.
            final var agreementId = agreement.getId();
            final var logItem = RdfConverter.toRdf(agreement);

            clearingHouseSvc.sendToClearingHouse(agreementId, logItem);
        } catch (IOException | RuntimeException exception) {
            if (log.isWarnEnabled()) {
                log.warn("Failed to send contract agreement to clearing house. "
                        + "[exception=({})]", exception.getMessage());
            }
        }
    }

    /**
     * Send a message to the clearing house. Allow the access only if that operation was successful.
     *
     * @param target      The target object.
     * @param agreementId The agreement id.
     * @throws PolicyExecutionException if the access could not be successfully logged.
     */
    public void logDataAccess(final URI target, final URI agreementId)
            throws PolicyExecutionException {
        try {
            final var logItem = buildLog(target);
            clearingHouseSvc.sendToClearingHouse(agreementId, logItem);
        } catch (Exception exception) {
            if (log.isWarnEnabled()) {
                log.warn("Failed to send log message to clearing house. [exception=({})]",
                        exception.getMessage());
            }
        }
    }

    /**
     * Send a message to the clearing house. Allow the access only if that operation was successful.
     *
     * @param rule    The ids rule.
     * @param element The accessed element.
     * @throws PolicyExecutionException If the notification has not been successful.
     */
    public void reportDataAccess(final Rule rule, final URI element)
            throws PolicyExecutionException {
        if (rule instanceof Permission permission) {
            final var postDuty = permission.getPostDuty().get(0);
            final var recipient = RuleUtils.getEndpoint(postDuty);

            notificationSvc.sendMessage(URI.create(recipient), buildLog(element));
        } else if (log.isWarnEnabled()) {
            log.warn("Reporting data access is only supported for permissions.");
        }
    }

    /**
     * Build a log information object.
     *
     * @param target The accessed element.
     * @return The log line.
     */
    public String buildLog(final URI target) {
        return new HashMap<String, Object>() {{
            put("target", target);
            put("issuerConnector", connectorSvc.getConnectorId());
            put("accessed", ZonedDateTime.now(ZoneOffset.UTC));
        }}.toString();
    }
}

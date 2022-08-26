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
package io.dataspaceconnector.config;

import de.fraunhofer.ids.messaging.core.daps.DapsVerifier;
import de.fraunhofer.ids.messaging.core.daps.customvalidation.ValidationRuleResult;
import io.dataspaceconnector.model.daps.Daps;
import io.dataspaceconnector.repository.DapsRepository;
import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

/**
 * This class adds a Daps validator for whitelisting of communication partner's DAPS to
 * the Messaging Services' DapsVerifier.
 */
@Log4j2
@Configuration
public class DapsConfig {

    /**
     * Connector DAPS URL.
     */
    @Value("${daps.url}")
    private String ownDapsUrl;

    /**
     * Daps repository.
     */
    private final @NonNull DapsRepository repository;

    /**
     * Constructor.
     *
     * @param pRepository the Daps repository.
     */
    public DapsConfig(@NonNull final DapsRepository pRepository) {
        this.repository = pRepository;
        initWhitelist();
    }

    /**
     * Method initializes Daps verifier check.
     */
    private void initWhitelist() {
        DapsVerifier.addValidationRule(claim -> {
                    if (isWhitelisted(claim)) {
                        return ValidationRuleResult.success();
                    }
                    return ValidationRuleResult.failure(
                            "Issuer DAPS '" + claim.getIssuer() + "' not whitelisted");
                }
        );
    }

    /**
     * Checks if the issuer of the given JTW claim is in the whitelisted DAPS list.
     * Backwards compatibility is given by returning true,
     * if the whitelist of DAPSs is empty or own DAPS is given.
     *
     * @param claim JWT token, whose issuer shall be validated
     * @return true if the whitelist is empty or the issues of the claim is
     * found in the whitelist or own DAPS is given, otherwise false
     */
    private boolean isWhitelisted(final Claims claim) {
        final var whitelistedDapsList = repository
                .findAll()
                .stream()
                .filter(daps -> Boolean.TRUE.equals(daps.getWhitelisted()))
                .map(Daps::getLocation)
                .map(URI::toString)
                .toList();

        //if no DAPS in whitelist, then trust all (even unknown)
        var trusted = whitelistedDapsList.isEmpty();

        if (!trusted) {
            //if DAPS in whitelist, then it must be either the own used or a whitelisted
            final var isConnectorDaps = ownDapsUrl.equals(claim.getIssuer());
            final var isWhitelisted = whitelistedDapsList.contains(claim.getIssuer());

            trusted = isConnectorDaps || isWhitelisted;
        }

        if (trusted && log.isInfoEnabled()) {
            log.info("Successfully validated DAPS whitelisting.");
        } else if (!trusted && log.isWarnEnabled()) {
            log.warn("Issuer DAPS of DAT of incoming message"
                    + " not whitelisted! [issuer=({})]", claim.getIssuer());
        }

        return trusted;
    }
}

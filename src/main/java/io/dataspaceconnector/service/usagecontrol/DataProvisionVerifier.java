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
import de.fraunhofer.iais.eis.SecurityProfile;
import io.dataspaceconnector.common.ids.policy.ContractUtils;
import io.dataspaceconnector.common.ids.policy.PolicyPattern;
import io.dataspaceconnector.common.ids.policy.RuleUtils;
import io.dataspaceconnector.common.exception.PolicyRestrictionException;
import io.dataspaceconnector.config.ConnectorConfig;
import io.dataspaceconnector.common.usagecontrol.PolicyVerifier;
import io.dataspaceconnector.common.usagecontrol.VerificationResult;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * A {@link PolicyVerifier} implementation that checks whether data provision should be allowed.
 */
@Component
@Log4j2
@RequiredArgsConstructor
public class DataProvisionVerifier implements PolicyVerifier<ProvisionVerificationInput> {

    /**
     * The policy execution point.
     */
    private final @NonNull RuleValidator ruleValidator;

    /**
     * Service for configuring policy settings.
     */
    private final @NonNull ConnectorConfig connectorConfig;

    /**
     * Policy check on data provision on provider side.
     *
     * @param target          The requested element.
     * @param issuerConnector The issuer connector.
     * @param agreement       The ids contract agreement.
     * @param profile         The security profile.
     * @throws PolicyRestrictionException If a policy restriction has been detected.
     */
    public void checkPolicy(final URI target, final URI issuerConnector,
                            final ContractAgreement agreement,
                            final Optional<SecurityProfile> profile)
            throws PolicyRestrictionException {
        final var patternsToCheck = Arrays.asList(
                PolicyPattern.PROVIDE_ACCESS,
                PolicyPattern.PROHIBIT_ACCESS,
                PolicyPattern.USAGE_DURING_INTERVAL,
                PolicyPattern.USAGE_UNTIL_DELETION,
                PolicyPattern.CONNECTOR_RESTRICTED_USAGE,
                PolicyPattern.SECURITY_PROFILE_RESTRICTED_USAGE);
        try {
            checkForAccess(patternsToCheck, target, issuerConnector, agreement, profile);
        } catch (PolicyRestrictionException exception) {
            // Unknown patterns cause an exception. Ignore if unsupported patterns are allowed.
            if (!connectorConfig.isAllowUnsupported()) {
                throw exception;
            }
        }
    }

    /**
     * Checks the contract content for data access (on provider side).
     *
     * @param patterns        List of patterns that should be enforced.
     * @param target          The requested element.
     * @param issuerConnector The issuer connector.
     * @param agreement       The ids contract agreement.
     * @param profile         The security profile.
     * @throws PolicyRestrictionException If a policy restriction has been detected.
     */
    public void checkForAccess(final List<PolicyPattern> patterns,
                               final URI target, final URI issuerConnector,
                               final ContractAgreement agreement,
                               final Optional<SecurityProfile> profile)
            throws PolicyRestrictionException {
        final var rules = ContractUtils.getRulesForTargetId(agreement, target);

        // Check the policy of each rule.
        for (final var rule : rules) {
            final var pattern = RuleUtils.getPatternByRule(rule);
            // Enforce only a set of patterns.
            if (patterns.contains(pattern)) {
                ruleValidator.validatePolicy(pattern, rule, target, issuerConnector, profile,
                        agreement.getId());
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VerificationResult verify(final ProvisionVerificationInput input) {
        try {
            this.checkPolicy(input.getTarget(), input.getIssuerConnector(), input.getAgreement(),
                    input.getSecurityProfile());
            return VerificationResult.ALLOWED;
        } catch (PolicyRestrictionException exception) {
            if (log.isDebugEnabled()) {
                log.debug("Data access denied. [input=({})]", input, exception);
            }
            return VerificationResult.DENIED;
        }
    }
}

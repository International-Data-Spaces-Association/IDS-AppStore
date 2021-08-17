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
package io.dataspaceconnector.service.resource.type;

import io.dataspaceconnector.common.exception.ErrorMessage;
import io.dataspaceconnector.common.util.Utils;
import io.dataspaceconnector.model.rule.ContractRule;
import io.dataspaceconnector.model.rule.ContractRuleDesc;
import io.dataspaceconnector.repository.RuleRepository;
import io.dataspaceconnector.service.resource.base.BaseEntityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Handles the basic logic for contract rules.
 */
@Service
@Transactional
public class RuleService extends BaseEntityService<ContractRule, ContractRuleDesc> {

    /**
     * Finds all rules in a specific contract.
     *
     * @param contractId ID of the contract
     * @return list of all rules in the contract
     */
    public List<ContractRule> getAllByContract(final UUID contractId) {
        Utils.requireNonNull(contractId, ErrorMessage.ENTITYID_NULL);
        return ((RuleRepository) getRepository()).findAllByContract(contractId);
    }

}

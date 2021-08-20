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
package io.dataspaceconnector.service.resource.type;

import io.dataspaceconnector.common.exception.ErrorMessage;
import io.dataspaceconnector.common.util.Utils;
import io.dataspaceconnector.model.contract.Contract;
import io.dataspaceconnector.model.contract.ContractDesc;
import io.dataspaceconnector.repository.ContractRepository;
import io.dataspaceconnector.service.resource.base.BaseEntityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Handles the basic logic for contracts.
 */
@Service
@Transactional
public class ContractService extends BaseEntityService<Contract, ContractDesc> {

    /**
     * Finds all contracts applicable for a specific artifact.
     *
     * @param artifactId ID of the artifact
     * @return list of contracts applicable for the artifact
     */
    public List<Contract> getAllByArtifactId(final UUID artifactId) {
        Utils.requireNonNull(artifactId, ErrorMessage.ENTITYID_NULL);
        return ((ContractRepository) getRepository()).findAllByArtifactId(artifactId);
    }

}

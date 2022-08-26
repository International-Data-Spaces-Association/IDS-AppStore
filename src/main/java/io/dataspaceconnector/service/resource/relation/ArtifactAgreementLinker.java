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
package io.dataspaceconnector.service.resource.relation;

import io.dataspaceconnector.model.agreement.Agreement;
import io.dataspaceconnector.model.artifact.Artifact;
import io.dataspaceconnector.service.resource.base.NonOwningRelationService;
import io.dataspaceconnector.service.resource.type.AgreementService;
import io.dataspaceconnector.service.resource.type.ArtifactService;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Handles the relation between artifacts and agreements.
 */
@Service
@NoArgsConstructor
public class ArtifactAgreementLinker extends NonOwningRelationService<Artifact, Agreement,
        ArtifactService, AgreementService> {

    @Override
    protected final List<Agreement> getInternal(final Artifact owner) {
        return owner.getAgreements();
    }
}

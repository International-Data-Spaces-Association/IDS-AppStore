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
package io.dataspaceconnector.controller.resource.relation;

import io.dataspaceconnector.config.BasePath;
import io.dataspaceconnector.config.BaseType;
import io.dataspaceconnector.controller.resource.base.BaseResourceChildRestrictedController;
import io.dataspaceconnector.controller.resource.base.tag.ResourceDescription;
import io.dataspaceconnector.controller.resource.base.tag.ResourceName;
import io.dataspaceconnector.controller.resource.view.contract.ContractView;
import io.dataspaceconnector.model.contract.Contract;
import io.dataspaceconnector.model.resource.RequestedResource;
import io.dataspaceconnector.service.resource.relation.AbstractResourceContractLinker;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Offers the endpoints for managing the relations between requested resources and contracts.
 */
@RestController
@RequestMapping(BasePath.REQUESTS + "/{id}/" + BaseType.CONTRACTS)
@Tag(name = ResourceName.REQUESTS, description = ResourceDescription.REQUESTS)
public class RequestedResourcesToContractsController extends BaseResourceChildRestrictedController<
        AbstractResourceContractLinker<RequestedResource>, Contract, ContractView> {
}

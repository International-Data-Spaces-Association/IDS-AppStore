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
package io.dataspaceconnector.controller.resource.view.resource;

import io.dataspaceconnector.config.BaseType;
import io.dataspaceconnector.controller.resource.relation.ResourcesToBrokersController;
import io.dataspaceconnector.controller.resource.relation.ResourcesToCatalogsController;
import io.dataspaceconnector.controller.resource.relation.ResourcesToContractsController;
import io.dataspaceconnector.controller.resource.relation.ResourcesToRepresentationsController;
import io.dataspaceconnector.controller.resource.relation.ResourcesToSubscriptionsController;
import io.dataspaceconnector.controller.resource.type.ResourceController;
import io.dataspaceconnector.controller.resource.view.util.SelfLinking;
import io.dataspaceconnector.controller.resource.view.util.ViewAssemblerHelper;
import io.dataspaceconnector.model.resource.Resource;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;

/**
 * Assembles the REST resource for a requested resource.
 */
@Component
@NoArgsConstructor
public class ResourceViewAssembler implements RepresentationModelAssembler<
        Resource, ResourceView>, SelfLinking {
    /**
     * Construct the ResourceView from a Resource.
     *
     * @param resource The resource.
     * @return The new view.
     */
    @Override
    public ResourceView toModel(final Resource resource) {
        final var modelMapper = new ModelMapper();
        final var view = modelMapper.map(resource, ResourceView.class);
        view.add(getSelfLink(resource.getId()));

        final var contractsLink = linkTo(methodOn(ResourcesToContractsController.class)
                .getResource(resource.getId(), null, null))
                .withRel(BaseType.CONTRACTS);
        view.add(contractsLink);

        final var representationLink = linkTo(methodOn(ResourcesToRepresentationsController.class)
                .getResource(resource.getId(), null, null))
                .withRel(BaseType.REPRESENTATIONS);
        view.add(representationLink);

        final var catalogLink = linkTo(methodOn(ResourcesToCatalogsController.class)
                .getResource(resource.getId(), null, null))
                .withRel(BaseType.CATALOGS);
        view.add(catalogLink);

        final var subscriptionLink = linkTo(methodOn(ResourcesToSubscriptionsController.class)
                .getResource(resource.getId(), null, null))
                .withRel(BaseType.SUBSCRIPTIONS);
        view.add(subscriptionLink);

        final var brokerLink = linkTo(methodOn(ResourcesToBrokersController.class)
                .getResource(resource.getId(), null, null))
                .withRel(BaseType.BROKERS);
        view.add(brokerLink);

        return view;
    }

    @Override
    public final Link getSelfLink(final UUID entityId) {
        return ViewAssemblerHelper.getSelfLink(entityId, ResourceController.class);
    }
}

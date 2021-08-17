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
package io.dataspaceconnector.controller.resource.view.representation;

import io.dataspaceconnector.config.BaseType;
import io.dataspaceconnector.controller.resource.relation.RepresentationsToAppsController;
import io.dataspaceconnector.controller.resource.relation.RepresentationsToArtifactsController;
import io.dataspaceconnector.controller.resource.relation.RepresentationsToResourcesController;
import io.dataspaceconnector.controller.resource.relation.RepresentationsToSubscriptionsController;
import io.dataspaceconnector.controller.resource.type.RepresentationController;
import io.dataspaceconnector.controller.resource.view.util.SelfLinking;
import io.dataspaceconnector.controller.resource.view.util.ViewAssemblerHelper;
import io.dataspaceconnector.model.representation.Representation;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;

/**
 * Assembles the REST resource for an representation.
 */
@Component
@NoArgsConstructor
public class RepresentationViewAssembler
        implements RepresentationModelAssembler<Representation, RepresentationView>, SelfLinking {
    /**
     * Construct the RepresentationView from an Representation.
     *
     * @param representation The representation.
     * @return The new view.
     */
    @Override
    public RepresentationView toModel(final Representation representation) {
        final var modelMapper = new ModelMapper();
        final var view = modelMapper.map(representation, RepresentationView.class);
        view.add(getSelfLink(representation.getId()));

        final var artifactsLink = linkTo(methodOn(RepresentationsToArtifactsController.class)
                .getResource(representation.getId(), null, null))
                .withRel(BaseType.ARTIFACTS);
        view.add(artifactsLink);

        final var resourceLink = linkTo(methodOn(RepresentationsToResourcesController.class)
                .getResource(representation.getId(), null, null))
                .withRel(BaseType.RESOURCES);
        view.add(resourceLink);

        final var subscriptionLink = linkTo(methodOn(RepresentationsToSubscriptionsController.class)
                .getResource(representation.getId(), null, null))
                .withRel(BaseType.SUBSCRIPTIONS);
        view.add(subscriptionLink);

        final var appLink = linkTo(methodOn(RepresentationsToAppsController.class)
                .getResource(representation.getId(), null, null))
                .withRel(BaseType.APPS);
        view.add(appLink);

        return view;
    }

    @Override
    public final Link getSelfLink(final UUID entityId) {
        return ViewAssemblerHelper.getSelfLink(entityId, RepresentationController.class);
    }
}

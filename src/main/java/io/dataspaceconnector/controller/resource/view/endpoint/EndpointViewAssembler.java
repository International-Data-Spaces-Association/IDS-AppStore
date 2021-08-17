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
package io.dataspaceconnector.controller.resource.view.endpoint;

import io.dataspaceconnector.config.BaseType;
import io.dataspaceconnector.controller.resource.relation.EndpointsToAppsController;
import io.dataspaceconnector.controller.resource.type.EndpointController;
import io.dataspaceconnector.controller.resource.view.util.SelfLinking;
import io.dataspaceconnector.controller.resource.view.util.ViewAssemblerHelper;
import io.dataspaceconnector.model.endpoint.Endpoint;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;

/**
 * Assembler for the Endpoint-View-Proxy.
 */
@Component
@NoArgsConstructor
public class EndpointViewAssembler
        implements RepresentationModelAssembler<Endpoint, EndpointView>, SelfLinking {

    /**
     * Construct the EndpointView from an Endpoint.
     *
     * @param endpoint The endpoint.
     * @return The new view.
     */
    @Override
    public EndpointView toModel(final Endpoint endpoint) {
        final var modelMapper = new ModelMapper();
        final var view = modelMapper.map(endpoint, EndpointView.class);
        view.add(getSelfLink(endpoint.getId()));

        final var appLink = linkTo(methodOn(EndpointsToAppsController.class)
                .getResource(endpoint.getId(), null, null))
                .withRel(BaseType.APPS);
        view.add(appLink);

        return view;
    }

    @Override
    public final Link getSelfLink(final UUID entityId) {
        return ViewAssemblerHelper.getSelfLink(entityId, EndpointController.class);
    }
}

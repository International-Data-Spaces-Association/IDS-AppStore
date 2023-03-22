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
package io.dataspaceconnector.controller.resource.view.daps;

import io.dataspaceconnector.controller.resource.type.DapsController;
import io.dataspaceconnector.controller.resource.view.util.SelfLinkHelper;
import io.dataspaceconnector.controller.resource.view.util.SelfLinking;
import io.dataspaceconnector.model.daps.Daps;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.UUID;


/**
 * Assembles the REST resource for a DAPS.
 */
@Component
public class DapsViewAssembler extends SelfLinkHelper
        implements RepresentationModelAssembler<Daps, DapsView>, SelfLinking {

    @Override
    public final Link getSelfLink(final UUID entityId) {
        return getSelfLink(entityId, DapsController.class);
    }

    @Override
    public final DapsView toModel(final Daps daps) {
        final var modelMapper = new ModelMapper();
        final var view = modelMapper.map(daps, DapsView.class);
        view.add(getSelfLink(daps.getId()));

        return view;
    }
}

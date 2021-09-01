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
package io.dataspaceconnector.controller.resource.view.subscription;

import io.dataspaceconnector.controller.resource.type.SubscriptionController;
import io.dataspaceconnector.controller.resource.view.util.SelfLinking;
import io.dataspaceconnector.controller.resource.view.util.ViewAssemblerHelper;
import io.dataspaceconnector.model.subscription.Subscription;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Assembles the REST resource for a subscription.
 */
@Component
@NoArgsConstructor
public class SubscriptionViewAssembler
        implements RepresentationModelAssembler<Subscription, SubscriptionView>, SelfLinking {

    /**
     * Constructs the SubscriberView from a Subscription.
     *
     * @param subscription the subscription.
     * @return the corresponding subscription view.
     */
    @Override
    public SubscriptionView toModel(final Subscription subscription) {
        final var modelMapper = new ModelMapper();
        final var view = modelMapper.map(subscription, SubscriptionView.class);
        view.add(getSelfLink(subscription.getId()));

        return view;
    }

    /**
     * Returns the self link for a subscription.
     *
     * @param entityId the ID of the subscription.
     * @return the self link.
     */
    @Override
    public Link getSelfLink(final UUID entityId) {
        return ViewAssemblerHelper.getSelfLink(entityId,
                SubscriptionController.class);
    }

}

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
package io.dataspaceconnector.service.resource.ids.builder;

import de.fraunhofer.iais.eis.SmartDataApp;
import de.fraunhofer.iais.eis.SmartDataAppBuilder;
import de.fraunhofer.iais.eis.util.ConstraintViolationException;
import io.dataspaceconnector.common.ids.mapping.ToIdsObjectMapper;
import io.dataspaceconnector.model.app.App;
import io.dataspaceconnector.service.resource.ids.builder.base.AbstractIdsBuilder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * Converts dsc apps to ids data apps.
 */
@Component
@RequiredArgsConstructor
public final class IdsDataAppBuilder extends AbstractIdsBuilder<App, SmartDataApp> {

    /**
     * The builder for ids dataApp.
     */
    private final @NonNull IdsEndpointBuilder endpointBuilder;

    @Override
    protected de.fraunhofer.iais.eis.SmartDataApp createInternal(final App app,
                                                                 final int currentDepth,
                                                                 final int maxDepth)
            throws ConstraintViolationException {

        // Build children.
        final var endpoints = create(endpointBuilder, app.getEndpoints(), currentDepth, maxDepth);

        if (endpoints.isEmpty() || endpoints.get().isEmpty()) {
            return null;
        }

        // Prepare SmartDataApp attributes.
        final var selfLink = getAbsoluteSelfLink(app);
        final var documentation = app.getDocs();
        final var environmentVariables = app.getEnvironmentVariables();
        final var storageConfiguration = app.getStorageConfig();

        final var usagePolicies
                = ToIdsObjectMapper.getListOfUsagePolicyClasses(app.getSupportedUsagePolicies());

        final var builder = new SmartDataAppBuilder(selfLink)
                ._appDocumentation_(documentation)
                ._appEnvironmentVariables_(environmentVariables)
                ._appStorageConfiguration_(storageConfiguration)
                ._supportedUsagePolicies_(usagePolicies);

        endpoints.ifPresent(x -> builder._appEndpoint_(Collections.unmodifiableList(x)));

        return builder.build();
    }
}

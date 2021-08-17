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
package io.dataspaceconnector.service.resource.ids.builder;

import de.fraunhofer.iais.eis.AppRepresentationBuilder;
import de.fraunhofer.iais.eis.IANAMediaTypeBuilder;
import de.fraunhofer.iais.eis.util.ConstraintViolationException;
import io.dataspaceconnector.common.ids.mapping.ToIdsObjectMapper;
import io.dataspaceconnector.model.representation.Representation;
import io.dataspaceconnector.service.resource.ids.builder.base.AbstractIdsBuilder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Collections;

/**
 * Converts dsc representation to ids representation.
 */
@Component
@RequiredArgsConstructor
public final class IdsRepresentationBuilder extends AbstractIdsBuilder<Representation,
        de.fraunhofer.iais.eis.AppRepresentation> {

    /**
     * The builder for ids artifacts.
     */
    private final @NonNull IdsArtifactBuilder artifactBuilder;

    /**
     * The builder for ids data apps.
     */
    private final @NonNull IdsDataAppBuilder dataAppBuilder;

    @Override
    protected de.fraunhofer.iais.eis.AppRepresentation createInternal(
            final Representation representation, final int currentDepth,
            final int maxDepth) throws ConstraintViolationException {
        // Build children.
        final var artifacts =
                create(artifactBuilder, representation.getArtifacts(), currentDepth, maxDepth);

        final var apps
                = create(dataAppBuilder, representation.getDataApps(), currentDepth, maxDepth);

        // Build representation only if at least one artifact and one data app is present.
        if (artifacts.isEmpty() || artifacts.get().isEmpty() || apps.isEmpty()
                || apps.get().isEmpty()) {
            return null;
        }

        // Prepare representation attributes.
        final var modified = ToIdsObjectMapper.getGregorianOf(representation
                .getModificationDate());
        final var created = ToIdsObjectMapper.getGregorianOf(representation
                .getCreationDate());
        final var language = ToIdsObjectMapper.getLanguage(representation.getLanguage());
        final var mediaType =
                new IANAMediaTypeBuilder()._filenameExtension_(representation.getMediaType())
                        .build();
        final var standard = URI.create(representation.getStandard());
        final var distributionService = representation.getDistributionService();
        final var runtimeEnvironment = representation.getRuntimeEnvironment();

        final var builder = new AppRepresentationBuilder(getAbsoluteSelfLink(representation))
                ._created_(created)
                ._dataAppDistributionService_(distributionService)
                ._dataAppRuntimeEnvironment_(runtimeEnvironment)
                ._language_(language)
                ._mediaType_(mediaType)
                ._modified_(modified)
                ._representationStandard_(standard);

        artifacts.ifPresent(x -> builder._instance_(Collections.unmodifiableList(x)));
        // FIXME: First element because DataApp is a list
        apps.ifPresent(x -> builder._dataAppInformation_(Collections.unmodifiableList(x).get(0)));
        return builder.build();
    }
}

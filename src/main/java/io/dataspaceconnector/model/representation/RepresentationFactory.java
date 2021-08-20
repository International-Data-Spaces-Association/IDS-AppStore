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
package io.dataspaceconnector.model.representation;

import io.dataspaceconnector.model.named.AbstractNamedFactory;
import io.dataspaceconnector.model.util.FactoryUtils;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.ArrayList;

/**
 * Creates and updates a representation.
 */
@Component
@Data
public class RepresentationFactory
        extends AbstractNamedFactory<Representation, RepresentationDesc> {

    /**
     * The default remote id assigned to all representations.
     */
    public static final URI DEFAULT_REMOTE_ID = URI.create("genesis");

    /**
     * The default language assigned to all representations.
     */
    public static final String DEFAULT_LANGUAGE = "EN";

    /**
     * The default media type assigned to all representations.
     */
    public static final String DEFAULT_MEDIA_TYPE = "";

    /**
     * The default standard assigned to all representations.
     */
    public static final String DEFAULT_STANDARD = "";

    /**
     * The default runtime environment to all representations.
     */
    public static final String DEFAULT_ENVIRONMENT = "docker";

    /**
     * The default distribution service assigned to all representations.
     */
    @Value("${registry.project}")
    private URI defaultDistributionService;

    /**
     * Create a new representation.
     *
     * @param desc The description of the new representation.
     * @return The new representation.
     * @throws IllegalArgumentException if desc is null.
     */
    @Override
    protected Representation initializeEntity(final RepresentationDesc desc) {
        final var representation = new Representation();
        representation.setArtifacts(new ArrayList<>());
        representation.setResources(new ArrayList<>());
        representation.setSubscriptions(new ArrayList<>());
        representation.setDataApps(new ArrayList<>());

        return representation;
    }

    /**
     * Update a representation.
     *
     * @param representation The representation to be updated.
     * @param desc           The new representation description.
     * @return True if the representation has been modified.
     * @throws IllegalArgumentException if any of the parameters is null.
     */
    @Override
    protected boolean updateInternal(final Representation representation,
                                     final RepresentationDesc desc) {
        final var hasUpdatedRemoteId = this.updateRemoteId(representation, desc.getRemoteId());
        final var hasUpdatedMediaType = this.updateMediaType(representation, desc.getMediaType());
        final var hasUpdatedLanguage = this.updateLanguage(representation, desc.getLanguage());
        final var hasUpdatedStandard = this.updateStandard(representation, desc.getStandard());
        final var hasUpdatedRuntimeEnvironment = this.updateRuntimeEnvironment(representation,
                desc.getRuntimeEnvironment());
        final var hasUpdatedDistributionService = this.updateDistributionService(representation,
                desc.getDistributionService());

        return hasUpdatedRemoteId || hasUpdatedLanguage || hasUpdatedMediaType
                || hasUpdatedStandard || hasUpdatedRuntimeEnvironment
                || hasUpdatedDistributionService;
    }

    private boolean updateRemoteId(final Representation representation, final URI remoteId) {
        final var newUri = FactoryUtils.updateUri(
                representation.getRemoteId(), remoteId, DEFAULT_REMOTE_ID);
        newUri.ifPresent(representation::setRemoteId);

        return newUri.isPresent();
    }

    private boolean updateLanguage(final Representation representation, final String language) {
        final var newLanguage = FactoryUtils.updateString(
                representation.getLanguage(), language, DEFAULT_LANGUAGE);
        newLanguage.ifPresent(representation::setLanguage);

        return newLanguage.isPresent();
    }

    private boolean updateMediaType(final Representation representation, final String mediaType) {
        final var newMediaType = FactoryUtils.updateString(representation.getMediaType(),
                mediaType, DEFAULT_MEDIA_TYPE);
        newMediaType.ifPresent(representation::setMediaType);

        return newMediaType.isPresent();
    }

    private boolean updateStandard(final Representation representation, final String standard) {
        final var newAdditional = FactoryUtils.updateString(representation.getStandard(),
                standard, DEFAULT_STANDARD);
        newAdditional.ifPresent(representation::setStandard);

        return newAdditional.isPresent();
    }

    private boolean updateRuntimeEnvironment(final Representation representation,
                                             final String runtimeEnvironment) {
        final var newRuntimeEnvironment = FactoryUtils.updateString(
                representation.getRuntimeEnvironment(), runtimeEnvironment, DEFAULT_ENVIRONMENT);
        newRuntimeEnvironment.ifPresent(representation::setRuntimeEnvironment);

        return newRuntimeEnvironment.isPresent();
    }

    private boolean updateDistributionService(final Representation representation,
                                              final URI service) {
        final var newUri = FactoryUtils.updateUri(representation.getDistributionService(),
                service, defaultDistributionService);
        newUri.ifPresent(representation::setDistributionService);

        return newUri.isPresent();
    }
}

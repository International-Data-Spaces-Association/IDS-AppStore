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
package io.dataspaceconnector.model.daps;

import io.dataspaceconnector.model.named.AbstractNamedFactory;
import io.dataspaceconnector.model.util.FactoryUtils;

import java.net.URI;

/**
 * Creates and updates a DAPS.
 */
public class DapsFactory extends AbstractNamedFactory<Daps, DapsDesc> {
    /**
     * Default location.
     */
    public static final URI DEFAULT_URI = URI.create("https://daps.com");

    /**
     * Default whitelisted setting.
     */
    public static final Boolean DEFAULT_WHITELISTED = false;

    /**
     * @param desc The description of the entity.
     * @return The new daps entity.
     */
    @Override
    protected Daps initializeEntity(final DapsDesc desc) {
        return new Daps();
    }

    /**
     * @param daps The entity to be updated.
     * @param desc The description of the new entity.
     * @return True, if daps is updated.
     */
    @Override
    protected boolean updateInternal(final Daps daps, final DapsDesc desc) {
        final var updatedLocation = updateLocation(daps, desc.getLocation());
        final var updatedWhitelisted = updateWhitelisted(daps, desc.getWhitelisted());

        return updatedLocation || updatedWhitelisted;
    }

    /**
     * @param daps     The entity to be updated.
     * @param location The new location url of the entity.
     * @return True, if daps is updated.
     */
    private boolean updateLocation(final Daps daps, final URI location) {
        final var newLocation = FactoryUtils.updateUri(daps.getLocation(), location,
                DEFAULT_URI);
        newLocation.ifPresent(daps::setLocation);

        return newLocation.isPresent();
    }

    /**
     * @param daps The entity to be updated.
     * @param whitelisted Whether to whitelist the DAPS or not.
     * @return True, if daps is updated.
     */
    private boolean updateWhitelisted(final Daps daps, final Boolean whitelisted) {
        final var newWhitelisted = FactoryUtils
                .updateBoolean(daps.getWhitelisted(), whitelisted, DEFAULT_WHITELISTED);
        newWhitelisted.ifPresent(daps::setWhitelisted);

        return newWhitelisted.isPresent();
    }
}

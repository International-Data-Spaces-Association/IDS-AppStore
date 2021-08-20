/*
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
package de.fraunhofer.fit.appstore.services.registry.webhook;

import de.fraunhofer.fit.appstore.exceptions.WebhookException;
import de.fraunhofer.fit.appstore.model.registry.webhook.Event;
import io.dataspaceconnector.common.util.UUIDUtils;
import io.dataspaceconnector.model.app.App;
import io.dataspaceconnector.model.app.AppDesc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Class with utilities for the registry webhook service.
 */
public final class RegistryWebhookUtils {

    /**
     * Error message for invalid events.
     */
    private static final String ERROR_MESSAGE = "Not a valid event!";

    /**
     * No args constructor.
     */
    private RegistryWebhookUtils() {
        // not used
    }

    /**
     * Check if an event is valid.
     *
     * @param event The event.
     * @throws WebhookException if the event is invalid.
     */
    public static void validateEvent(final Event event) throws WebhookException {
        if (event.getType() == null) {
            throw new WebhookException("The event type cannot be null." + ERROR_MESSAGE);
        }
        if (event.getEventData() == null) {
            throw new WebhookException("The event data cannot be null." + ERROR_MESSAGE);
        }

        if (event.getEventData().getRepository() == null) {
            throw new WebhookException("The event repository cannot be null." + ERROR_MESSAGE);
        }

        if (event.getEventData().getResources() == null) {
            throw new WebhookException("The event resources cannot be null." + ERROR_MESSAGE);
        }
    }

    /**
     * Update repository name if new value was set. Otherwise, copy old value.
     *
     * @param app      The app.
     * @param desc     The app desc.
     * @param newValue The new value.
     */
    public static void updateRepositoryName(final App app, final AppDesc desc,
                                            final String newValue) {
        if (newValue != null) {
            desc.setRepositoryName(newValue);
        } else {
            desc.setRepositoryName(app.getRepositoryName());
        }
    }

    /**
     * Update repository namespace if new value was set. Otherwise, copy old value.
     *
     * @param app      The app.
     * @param desc     The app desc.
     * @param newValue The new value.
     */
    public static void updateRepositoryNamespace(final App app, final AppDesc desc,
                                                 final String newValue) {
        if (newValue != null) {
            desc.setRepositoryNameSpace(newValue);
        } else {
            desc.setRepositoryNameSpace(app.getRepositoryNameSpace());
        }
    }

    /**
     * Update repository digest if new value was set. Otherwise, copy old value.
     *
     * @param app      The app.
     * @param desc     The app desc.
     * @param newValue The new value.
     */
    public static void updateRepositoryDigest(final App app, final AppDesc desc,
                                              final String newValue) {
        if (newValue != null) {
            desc.setRepositoryDigest(newValue);
        } else {
            desc.setRepositoryDigest(app.getRepositoryDigest());
        }
    }

    /**
     * Extract a list of uuids from a string.
     *
     * @param input The string that may contain uuids.
     * @return A list of found uuids.
     */
    public static List<UUID> extractIdsFromString(final String input) {
        final var uuids = UUIDUtils.findUuids(input);
        if (uuids.isEmpty() || uuids.size() < 2) {
            throw new WebhookException("IDs could not be extracted by the AppStore.");
        }

        final var list = new ArrayList<UUID>();
        for (final var id : uuids) {
            list.add(UUID.fromString(id));
        }
        return list;
    }
}

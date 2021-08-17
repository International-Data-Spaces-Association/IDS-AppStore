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
package io.dataspaceconnector.model.endpoint;

import io.dataspaceconnector.model.named.AbstractNamedFactory;
import io.dataspaceconnector.model.util.FactoryUtils;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.ArrayList;

/**
 * Base class for creating and updating endpoints.
 */
@Component
public class EndpointFactory extends AbstractNamedFactory<Endpoint, EndpointDesc> {

    /**
     * Default remote id assigned to all endpoints.
     */
    public static final URI DEFAULT_REMOTE_ID = URI.create("genesis");

    /**
     * The default uri.
     */
    public static final URI DEFAULT_URI = URI.create("https://documentation");

    /**
     * The default information.
     */
    public static final String DEFAULT_INFORMATION = "information";

    /**
     * The default port.
     */
    private static final Integer DEFAULT_PORT = 8080;

    /**
     * The default media type.
     */
    private static final String DEFAULT_MEDIA_TYPE = "application/json";

    /**
     * The default protocol.
     */
    private static final String DEFAULT_PROTOCOL = "HTTP/1.1";

    /**
     * The default path.
     */
    private static final String DEFAULT_PATH = "";

    /**
     * The default type.
     */
    private static final EndpointType DEFAULT_TYPE = EndpointType.INPUT_ENDPOINT;

    /**
     * Create a new endpoint.
     *
     * @param desc The description of the new endpoint.
     * @return The new endpoint.
     * @throws IllegalArgumentException if desc is null.
     */
    protected Endpoint initializeEntity(final EndpointDesc desc) {
        final var endpoint = new Endpoint();
        endpoint.setApps(new ArrayList<>());

        return endpoint;
    }

    /**
     * Update a endpoint.
     *
     * @param endpoint The endpoint to be updated.
     * @param desc     The description of the new endpoint.
     * @return True, if endpoint is updated.
     */
    @Override
    protected boolean updateInternal(final Endpoint endpoint, final EndpointDesc desc) {
        final var hasUpdatedRemoteId = updateRemoteId(endpoint, desc.getRemoteId());
        final var hasUpdatedLocation = updateLocation(endpoint, desc.getLocation());
        final var hasUpdatedDocs = updateDocs(endpoint, desc.getDocs());
        final var hasUpdatedInfo = updateInfo(endpoint, desc.getInfo());
        final var hasUpdatedMediaType = updateMediaType(endpoint, desc.getMediaType());
        final var hasUpdatedPort = updatePort(endpoint, desc.getPort());
        final var hasUpdatedProtocol = updateProtocol(endpoint, desc.getProtocol());
        final var hasUpdatedEndpointType = updateType(endpoint, desc.getType());
        final var hasUpdatedPath = updatePath(endpoint, desc.getPath());

        return hasUpdatedLocation || hasUpdatedDocs || hasUpdatedInfo
                || hasUpdatedMediaType || hasUpdatedPort || hasUpdatedProtocol
                || hasUpdatedEndpointType || hasUpdatedPath || hasUpdatedRemoteId;
    }

    /**
     * @param endpoint The endpoint entity.
     * @param info     The endpoint information.
     * @return True, if endpoint information is updated.
     */
    private boolean updateInfo(final Endpoint endpoint,
                               final String info) {
        final var newInfo =
                FactoryUtils.updateString(endpoint.getInfo(), info, DEFAULT_INFORMATION);
        newInfo.ifPresent(endpoint::setInfo);

        return newInfo.isPresent();
    }


    /**
     * @param endpoint The endpoint entity.
     * @param docs     The endpoint documentation.
     * @return True, if endpoint documentation is updated.
     */
    private boolean updateDocs(final Endpoint endpoint,
                               final URI docs) {
        final var newDocs = FactoryUtils.updateUri(endpoint.getDocs(), docs, DEFAULT_URI);
        newDocs.ifPresent(endpoint::setDocs);

        return newDocs.isPresent();
    }

    /**
     * @param endpoint The endpoint entity.
     * @param location The endpoint location.
     * @return True, if endpoint location is updated.
     */
    private boolean updateLocation(final Endpoint endpoint,
                                   final URI location) {
        final var newLocation = FactoryUtils.updateUri(endpoint.getDocs(), location, DEFAULT_URI);
        newLocation.ifPresent(endpoint::setLocation);

        return newLocation.isPresent();
    }

    /**
     * @param endpoint The endpoint entity.
     * @param path     The endpoint path.
     * @return True, if endpoint path is updated.
     */
    private boolean updatePath(final Endpoint endpoint, final String path) {
        final var newPath = FactoryUtils.updateString(endpoint.getPath(), path, DEFAULT_PATH);
        newPath.ifPresent(endpoint::setPath);

        return newPath.isPresent();
    }

    /**
     * @param endpoint  The endpoint entity.
     * @param mediaType The endpoint media type.
     * @return True, if endpoint media type is updated.
     */
    private boolean updateMediaType(final Endpoint endpoint, final String mediaType) {
        final var newMediaType = FactoryUtils.updateString(endpoint.getMediaType(),
                mediaType, DEFAULT_MEDIA_TYPE);
        newMediaType.ifPresent(endpoint::setMediaType);

        return newMediaType.isPresent();
    }

    /**
     * @param endpoint The endpoint entity.
     * @param protocol The endpoint protocol.
     * @return True, if endpoint protocol is updated.
     */
    private boolean updateProtocol(final Endpoint endpoint, final String protocol) {
        final var newProtocol = FactoryUtils.updateString(endpoint.getProtocol(),
                protocol, DEFAULT_PROTOCOL);
        newProtocol.ifPresent(endpoint::setProtocol);

        return newProtocol.isPresent();
    }

    /**
     * @param endpoint The endpoint entity.
     * @param type     The endpoint type.
     * @return True, if endpoint type is updated.
     */
    private boolean updateType(final Endpoint endpoint, final EndpointType type) {
        final var tmp = type == null ? DEFAULT_TYPE : type;
        if (tmp.equals(endpoint.getType())) {
            return false;
        }
        endpoint.setType(tmp);
        return true;
    }

    /**
     * @param endpoint The endpoint entity.
     * @param port     The endpoint port.
     * @return True, if endpoint port is updated.
     */
    private boolean updatePort(final Endpoint endpoint, final Long port) {
        final var tmp = port == null ? DEFAULT_PORT : port;

        final var newPort = FactoryUtils.updateNumber(endpoint.getPort(), tmp);
        if (newPort == endpoint.getPort()) {
            return false;
        }
        endpoint.setPort(newPort);
        return true;
    }

    private boolean updateRemoteId(final Endpoint endpoint, final URI remoteId) {
        final var newUri =
                FactoryUtils.updateUri(endpoint.getRemoteId(), remoteId, DEFAULT_REMOTE_ID);
        newUri.ifPresent(endpoint::setRemoteId);

        return newUri.isPresent();
    }
}

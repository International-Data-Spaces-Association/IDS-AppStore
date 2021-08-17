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

import de.fraunhofer.iais.eis.AppEndpointBuilder;
import de.fraunhofer.iais.eis.IANAMediaTypeBuilder;
import de.fraunhofer.iais.eis.util.ConstraintViolationException;
import de.fraunhofer.iais.eis.util.TypedLiteral;
import de.fraunhofer.iais.eis.util.Util;
import io.dataspaceconnector.common.ids.mapping.ToIdsObjectMapper;
import io.dataspaceconnector.model.endpoint.Endpoint;
import io.dataspaceconnector.service.resource.ids.builder.base.AbstractIdsBuilder;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

/**
 * Converts DSC endpoints to IDS endpoints.
 */
@Component
public final class IdsEndpointBuilder
        extends AbstractIdsBuilder<Endpoint, de.fraunhofer.iais.eis.AppEndpoint> {

    @Override
    protected de.fraunhofer.iais.eis.AppEndpoint createInternal(
            final Endpoint endpoint, final int currentDepth, final int maxDepth)
            throws ConstraintViolationException {

        final var protocol = endpoint.getProtocol();
        final var type = ToIdsObjectMapper.getAppEndpointType(endpoint.getType());
        final var port = endpoint.getPort();
        final var documentation = endpoint.getDocs();
        final var information = endpoint.getInfo();
        final var path = endpoint.getPath();
        final var location = endpoint.getLocation();
        final var mediaType =
                new IANAMediaTypeBuilder()._filenameExtension_(endpoint.getMediaType()).build();

        final var builder = new AppEndpointBuilder(getAbsoluteSelfLink(endpoint))
                ._endpointDocumentation_(Util.asList(documentation))
                ._endpointInformation_(Util.asList(new TypedLiteral(information, "EN")))
                ._appEndpointProtocol_(protocol)
                ._appEndpointType_(type)
                ._appEndpointPort_(BigInteger.valueOf(port))
                ._path_(path)
                ._accessURL_(location)
                ._appEndpointMediaType_(mediaType);

        return builder.build();
    }

}

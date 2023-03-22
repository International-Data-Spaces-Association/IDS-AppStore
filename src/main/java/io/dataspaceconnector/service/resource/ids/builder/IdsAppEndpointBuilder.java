/*
 * Copyright 2020-2022 Fraunhofer Institute for Software and Systems Engineering
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
import de.fraunhofer.iais.eis.AppEndpointType;
import de.fraunhofer.iais.eis.IANAMediaTypeBuilder;
import de.fraunhofer.iais.eis.util.ConstraintViolationException;
import de.fraunhofer.iais.eis.util.TypedLiteral;
import de.fraunhofer.iais.eis.util.Util;
import io.dataspaceconnector.common.ids.mapping.ToIdsObjectMapper;
import io.dataspaceconnector.common.net.SelfLinkHelper;
import io.dataspaceconnector.model.endpoint.AppEndpoint;
import io.dataspaceconnector.model.endpoint.AppEndpointImpl;
import io.dataspaceconnector.service.resource.ids.builder.base.AbstractIdsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.net.URI;

/**
 * Converts dsc endpoints to ids endpoints.
 */
@Component
public final class IdsAppEndpointBuilder
        extends AbstractIdsBuilder<AppEndpointImpl, de.fraunhofer.iais.eis.AppEndpointImpl> {

    /**
     * Constructs an IdsEndpointBuilder.
     *
     * @param selfLinkHelper the self link helper.
     */
    @Autowired
    public IdsAppEndpointBuilder(final SelfLinkHelper selfLinkHelper) {
        super(selfLinkHelper);
    }

//    @Override
//    protected de.fraunhofer.iais.eis.AppEndpointImpl createInternal(
//            final Endpoint endpoint, final int currentDepth, final int maxDepth)
//            throws ConstraintViolationException {
//
//        final var documentation = endpoint.getDocs();
//        var location = endpoint.getLocation();
//        final var info = new TypedLiteral(endpoint.getInfo(), "EN");
//
//        URI accessUrl;
//        try {
//            accessUrl = URI.create(location);
//        } catch (IllegalArgumentException exception) {
//            accessUrl = URI.create("https://default-url");
//        }
//
//
//
//            final var protocol = ((AppEndpoint) endpoint).getProtocol();
//            final var type = ((AppEndpoint) endpoint).getEndpointType();
//            final var port = ((AppEndpoint) endpoint).getEndpointPort();
//            final var information = endpoint.getInfo();
//            final var path = ((AppEndpoint) endpoint).getPath();
//            final var mediaType =
//                    new IANAMediaTypeBuilder()._filenameExtension_(((AppEndpoint) endpoint).getMediaType());
//            final var appEndpoint = (AppEndpoint) endpoint;
//
//        final var idsEndpoint = new AppEndpointBuilder(getAbsoluteSelfLink(endpoint))
//                    ._path_(location)
//                    ._accessURL_(accessUrl)
//                    ._endpointDocumentation_(Util.asList(documentation))
//                    ._endpointInformation_(Util.asList(info))
//                    ._appEndpointType_(AppEndpointType.valueOf(appEndpoint.getEndpointType()))
//                    ._appEndpointPort_(BigInteger.valueOf(appEndpoint.getEndpointPort()))
//                    ._appEndpointProtocol_(appEndpoint.getProtocol())
//                    ._appEndpointMediaType_(new IANAMediaTypeBuilder()
//                            ._filenameExtension_(appEndpoint.getMediaType())
//                            .build())
//                    ._language_(ToIdsObjectMapper.getLanguage(appEndpoint.getLanguage()))
//                    .build();
//
//
//        return idsEndpoint;
//    }

//    @Override
//    protected de.fraunhofer.iais.eis.AppEndpointImpl createInternal(AppEndpointImpl entity, int currentDepth, int maxDepth) throws ConstraintViolationException {
//        return null;
//    }

    @Override
    protected de.fraunhofer.iais.eis.AppEndpointImpl createInternal(AppEndpointImpl endpoint, int currentDepth, int maxDepth) throws ConstraintViolationException {
        final var documentation = endpoint.getDocs();
        var location = endpoint.getLocation();
        final var info = new TypedLiteral(endpoint.getInfo(), "EN");

        URI accessUrl;
        try {
            accessUrl = URI.create(location);
        } catch (IllegalArgumentException exception) {
            accessUrl = URI.create("https://default-url");
        }



//            final var protocol = ((AppEndpoint) endpoint).getProtocol();
//            final var type = ((AppEndpoint) endpoint).getEndpointType();
//            final var port = ((AppEndpoint) endpoint).getEndpointPort();
//            final var information = endpoint.getInfo();
//            final var path = ((AppEndpoint) endpoint).getPath();
//            final var mediaType =
//                    new IANAMediaTypeBuilder()._filenameExtension_(((AppEndpoint) endpoint).getMediaType());
            final var appEndpoint = (AppEndpoint) endpoint;

        final var idsEndpoint = new AppEndpointBuilder(getAbsoluteSelfLink(endpoint))
                    ._path_(location)
                    ._accessURL_(accessUrl)
                    ._endpointDocumentation_(Util.asList(documentation))
                    ._endpointInformation_(Util.asList(info))
                    ._appEndpointType_(AppEndpointType.INPUT_ENDPOINT)
                    ._appEndpointPort_(BigInteger.valueOf(appEndpoint.getEndpointPort()))
                    ._appEndpointProtocol_(appEndpoint.getProtocol())
                    ._appEndpointMediaType_(new IANAMediaTypeBuilder()
                            ._filenameExtension_(appEndpoint.getMediaType())
                            .build())
                    ._language_(ToIdsObjectMapper.getLanguage(appEndpoint.getLanguage()))
                    .build();


        return (de.fraunhofer.iais.eis.AppEndpointImpl) idsEndpoint;
    }
}

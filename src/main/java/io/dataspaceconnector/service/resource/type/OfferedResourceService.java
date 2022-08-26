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
package io.dataspaceconnector.service.resource.type;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.dataspaceconnector.model.base.AbstractFactory;
import io.dataspaceconnector.model.resource.*;
import io.dataspaceconnector.repository.BaseEntityRepository;
import io.dataspaceconnector.repository.OfferedResourcesRepository;
import io.dataspaceconnector.service.resource.base.RemoteResolver;
import io.dataspaceconnector.repository.RequestedResourcesRepository;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

/**
 * Handles the basic logic for offered resources.
 */
public class OfferedResourceService extends ResourceService<OfferedResource, OfferedResourceDesc>    {

    /**
     * Constructor.
     *
     * @param repository The offered resource repository.
     */
    @SuppressFBWarnings("MC_OVERRIDABLE_METHOD_CALL_IN_CONSTRUCTOR")
    public OfferedResourceService(final BaseEntityRepository<OfferedResource> repository) {
        this(repository, new OfferedResourceFactory());
        ((OfferedResourceFactory) this.getFactory()).setDoesExist(super::doesExist);
    }

    /**
     * Constructor.
     *
     * @param repository The offered resource repository.
     * @param factory    The offered resource factory.
     */
    public OfferedResourceService(
            final BaseEntityRepository<OfferedResource> repository,
            final AbstractFactory<OfferedResource, OfferedResourceDesc> factory) {
        super(repository, factory);
    }

//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public Optional<UUID> identifyByRemoteId(final URI remoteId) {
//        return ((OfferedResourcesRepository) getRepository()).identifyByRemoteId(remoteId);
//    }
//
//    /**
//     * Find offered resource by remote id.
//     *
//     * @param remoteId The remote id.
//     * @return The entity.
//     */
//    public Optional<OfferedResource> getEntityByRemoteId(final URI remoteId) {
//        final var repo = (OfferedResourcesRepository) getRepository();
//        return repo.getByRemoteId(remoteId);
//    }

//    // ToCheck
//    @Override
//    public Optional<UUID> identifyByRemoteId(URI remoteId) {
////        return Optional.empty();
////        return ((RequestedResourcesRepository) getRepository()).identifyByRemoteId(remoteId);
//        return ( this.getRepository()).identifyByRemoteId(remoteId);
//
//    }
//
//    /**
//     * Find requested resource by remote id.
//     *
//     * @param remoteId The remote id.
//     * @return The entity.
//     */
//    public Optional<Resource> getEntityByRemoteId(final URI remoteId) {
//        final var repo = (RequestedResourcesRepository) getRepository();
//        return repo.getByRemoteId(remoteId);
//    }

}

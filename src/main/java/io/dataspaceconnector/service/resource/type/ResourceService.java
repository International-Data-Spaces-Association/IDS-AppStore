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
package io.dataspaceconnector.service.resource.type;

import io.dataspaceconnector.model.resource.Resource;
import io.dataspaceconnector.model.resource.ResourceDesc;
import io.dataspaceconnector.repository.ResourceRepository;
import io.dataspaceconnector.service.resource.base.BaseEntityService;
import io.dataspaceconnector.service.resource.base.RemoteResolver;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

/**
 * Handles the basic logic for resources.
 */
@Service
@Transactional
public class ResourceService extends BaseEntityService<Resource, ResourceDesc>
        implements RemoteResolver {

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<UUID> identifyByRemoteId(final URI remoteId) {
        return ((ResourceRepository) getRepository()).identifyByRemoteId(remoteId);
    }

    /**
     * Find requested resource by remote id.
     *
     * @param remoteId The remote id.
     * @return The entity.
     */
    public Optional<Resource> getEntityByRemoteId(final URI remoteId) {
        final var repo = (ResourceRepository) getRepository();
        return repo.getByRemoteId(remoteId);
    }
}

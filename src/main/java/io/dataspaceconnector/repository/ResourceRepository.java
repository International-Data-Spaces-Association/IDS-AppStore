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
package io.dataspaceconnector.repository;

import io.dataspaceconnector.model.resource.Resource;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.net.URI;
import java.util.Optional;

/**
 * The repository containing all objects of type {@link Resource}.
 */
@Repository
public interface ResourceRepository extends RemoteEntityRepository<Resource> {

    /**
     * Find an entity by its remote id.
     *
     * @param remoteId The remote id.
     * @return The entity.
     */
    @Query("SELECT a "
            + "FROM Resource a "
            + "WHERE a.remoteId = :remoteId "
            + "AND a.deleted = false")
    Optional<Resource> getByRemoteId(URI remoteId);
}

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
package io.dataspaceconnector.service.resource.type;

import io.dataspaceconnector.model.base.AbstractFactory;
import io.dataspaceconnector.model.daps.Daps;
import io.dataspaceconnector.model.daps.DapsDesc;
import io.dataspaceconnector.repository.BaseEntityRepository;
import io.dataspaceconnector.service.resource.base.BaseEntityService;

/**
 * Service class for daps.
 */
public class DapsService extends BaseEntityService<Daps, DapsDesc> {

    /**
     * Constructor.
     *
     * @param repository The daps repository.
     * @param factory    The daps object logic.
     */
    public DapsService(final BaseEntityRepository<Daps> repository,
                         final AbstractFactory<Daps, DapsDesc> factory) {
        super(repository, factory);
    }
}

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
package io.dataspaceconnector.service.resource.relation;

import io.dataspaceconnector.model.catalog.Catalog;
import io.dataspaceconnector.model.resource.Resource;
import io.dataspaceconnector.service.resource.base.OwningRelationService;
import io.dataspaceconnector.service.resource.type.CatalogService;
import io.dataspaceconnector.service.resource.type.ResourceService;

/**
 * Base class for handling catalog-resource relations.
 *
 * @param <T> The resource type.
 */
public abstract class AbstractCatalogResourceLinker<T extends Resource>
        extends OwningRelationService<Catalog, T, CatalogService, ResourceService<T, ?>> {
    /**
     * Default constructor.
     */
    protected AbstractCatalogResourceLinker() {
        super();
    }
}

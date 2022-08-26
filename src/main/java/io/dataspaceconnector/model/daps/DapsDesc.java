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

import io.dataspaceconnector.model.named.NamedDescription;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.net.URI;

/**
 * Describing DAPS properties.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DapsDesc extends NamedDescription {

    /**
     * The url location of the DAPS.
     */
    private URI location;

    /**
     * Whether this daps is whitelisted.
     */
    private Boolean whitelisted;
}

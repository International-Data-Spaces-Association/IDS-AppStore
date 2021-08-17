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
package io.dataspaceconnector.model.resource;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Enumeration for resource payment modality.
 */
public enum PaymentMethod {

    /**
     * To express that the payment method is not set.
     */
    @JsonProperty("undefined")
    UNDEFINED,

    /**
     * To express that the exchange of resource is with a fixed price.
     */
    @JsonProperty("fixedPrice")
    FIXED_PRICE,

    /**
     * To express that the exchange of resource is free.
     */
    @JsonProperty("free")
    FREE,

    /**
     * To express that the exchange of resource is negotiation-based.
     */
    @JsonProperty("negotiationBasis")
    NEGOTIATION_BASIS
}

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
package io.dataspaceconnector.model.resource;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dataspaceconnector.model.named.NamedDescription;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.net.URI;
import java.util.List;

/**
 * Base class for describing resources.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ResourceDesc extends NamedDescription {

    /**
     * The keywords of the resource.
     */
    private List<String> keywords;

    /**
     * The publisher of the resource.
     */
    private URI publisher;

    /**
     * The language of the resource.
     */
    private String language;

    /**
     * The license of the resource.
     */
    private URI license;

    /**
     * The owner of the resource.
     */
    private URI sovereign;

    /**
     * The endpoint of the resource.
     */
    private URI endpointDocumentation;

    /**
     * The payment modality.
     */
    @JsonProperty("paymentMethod")
    @JsonAlias("paymentModality")
    private PaymentMethod paymentMethod;

    /**
     * A list of resource IDs pointing at sample resources.
     */
    private List<URI> samples;
}

/*
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
package de.fraunhofer.fit.appstore.model.registry.webhook;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Entity for summary details.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SummaryDetail implements Serializable {

    /**
     * Default serial version uid.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The number of unknown issues.
     */
    @JsonProperty("unknown")
    private int unknown;

    /**
     * The number of negligible issues.
     */
    @JsonProperty("Negligible")
    private int negligible;

    /**
     * The number of low issues.
     */
    @JsonProperty("Low")
    private int low;

    /**
     * The number of medium issues.
     */
    @JsonProperty("Medium")
    private int medium;

    /**
     * The number of high issues.
     */
    @JsonProperty("High")
    private int high;

    /**
     * The number of critical issues.
     */
    @JsonProperty("Critical")
    private int critical;

}

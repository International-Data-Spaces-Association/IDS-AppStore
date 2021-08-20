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
import java.util.Date;
import java.util.UUID;

/**
 * Entity that contains scan properties.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScanOverview implements Serializable {

    /**
     * Default serial version uid.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The report id.
     */
    @JsonProperty("report_id")
    private UUID reportId;

    /**
     * The scan status.
     */
    @JsonProperty("scan_status")
    private String scanStatus;

    /**
     * The severity.
     */
    @JsonProperty("severity")
    private String severity;

    /**
     * The duration.
     */
    @JsonProperty("duration")
    private int duration;

    /**
     * The summary.
     */
    @JsonProperty("summary")
    private Summary summary;

    /**
     * The start time.
     */
    @JsonProperty("start_time")
    private Date startTime;

    /**
     * The end time.
     */
    @JsonProperty("end_time")
    private Date endTime;

    /**
     * The scanner.
     */
    @JsonProperty("scanner")
    private Scanner scanner;

    /**
     * The complete percent.
     */
    @JsonProperty("complete_percent")
    private int completePercent;


}

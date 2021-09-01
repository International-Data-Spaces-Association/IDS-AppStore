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
package de.fraunhofer.fit.appstore.model.registry.securityScan;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Security scanner result.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecurityScanResult implements Serializable {

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
     * The scan duration.
     */
    @JsonProperty("duration")
    private int duration;

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
     * The complete percent.
     */
    @JsonProperty("complete_percent")
    private int completePercent;

    /**
     * The scanner.
     */
    @JsonProperty("scanner")
    private SecurityScannerInformation scanner;

    /**
     * The summary.
     */
    @JsonProperty("summary")
    private SecurityScanSummary summary;
}

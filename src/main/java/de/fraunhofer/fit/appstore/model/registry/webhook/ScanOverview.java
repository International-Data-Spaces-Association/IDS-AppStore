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

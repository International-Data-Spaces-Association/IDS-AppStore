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

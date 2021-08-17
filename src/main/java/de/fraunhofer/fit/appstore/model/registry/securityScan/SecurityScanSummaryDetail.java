package de.fraunhofer.fit.appstore.model.registry.securityScan;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * The security scan summary details.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecurityScanSummaryDetail implements Serializable {

    /**
     * Default serial version uid.
     */
    private static final long serialVersionUID = 1L;

    /**
     * An indicator for a low risk issue.
     */
    @JsonProperty("Low")
    private int low;

    /**
     * An indicator for a medium risk issue.
     */
    @JsonProperty("Medium")
    private int medium;

    /**
     * An indicator for a high risk issue.
     */
    @JsonProperty("High")
    private int high;
}

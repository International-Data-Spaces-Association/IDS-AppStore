package de.fraunhofer.fit.appstore.model.registry.securityScan;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * The security scan summary.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecurityScanSummary implements Serializable {

    /**
     * Default serial version uid.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The total number of issues.
     */
    @JsonProperty("total")
    private int total;

    /**
     * The number of fixable issues.
     */
    @JsonProperty("fixable")
    private int fixable;

    /**
     * The summary details.
     */
    @JsonProperty("summary")
    private SecurityScanSummaryDetail summary;
}

package de.fraunhofer.fit.appstore.model.registry.webhook;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * The resources.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resources implements Serializable {

    /**
     * Default serial version uid.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The digest.
     */
    @JsonProperty("digest")
    private String digest;

    /**
     * The tag.
     */
    @JsonProperty("tag")
    private String tag;

    /**
     * The resource url.
     */
    @JsonProperty("resource_url")
    private String resourceUrl;

    /**
     * The scan overview.
     */
    @JsonProperty("scan_overview")
    private ScanOverview scanOverview;


}

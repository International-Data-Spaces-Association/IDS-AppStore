package de.fraunhofer.fit.appstore.model.registry.webhook;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Entity that contains scanner properties.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Scanner implements Serializable {

    /**
     * Default serial version uid.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The name.
     */
    @JsonProperty("name")
    private String name;

    /**
     * The vendor.
     */
    @JsonProperty("vendor")
    private String vendor;

    /**
     * The version.
     */
    @JsonProperty("version")
    private String version;

}

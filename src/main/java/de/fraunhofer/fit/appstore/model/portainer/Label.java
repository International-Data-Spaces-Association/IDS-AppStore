package de.fraunhofer.fit.appstore.model.portainer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Entity for mapping label attributes.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Label implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    /**
     * The label.
     */
    @JsonProperty("label")
    private String label;

    /**
     * The value.
     */
    @JsonProperty("value")
    private String value;

}

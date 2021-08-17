package de.fraunhofer.fit.appstore.model.portainer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Entity for mapping volume attributes.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Volume implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    /**
     * The container.
     */
    @JsonProperty("container")
    private String container;

    /**
     * The binding.
     */
    @JsonProperty("bind")
    private String bind;
}

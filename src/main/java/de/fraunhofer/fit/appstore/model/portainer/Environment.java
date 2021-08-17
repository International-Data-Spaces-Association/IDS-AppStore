package de.fraunhofer.fit.appstore.model.portainer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Entity for mapping environment attributes.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Environment implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    /**
     * The name of the environment.
     */
    @JsonProperty("name")
    private String name;

    /**
     * The label of the environment.
     */
    @JsonProperty("label")
    private String label;

    /**
     * The default value of the environment.
     */
    @JsonProperty("default")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String defaultValue = null;

}

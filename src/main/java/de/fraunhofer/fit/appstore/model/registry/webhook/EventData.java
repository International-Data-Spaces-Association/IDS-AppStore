package de.fraunhofer.fit.appstore.model.registry.webhook;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * The event data.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventData implements Serializable {

    /**
     * Default serial version uid.
     */
    private static final long serialVersionUID = 1L;

    /**
     * A list of resources.
     */
    @JsonProperty("resources")
    private Resources[] resources;

    /**
     * The repository.
     */
    @JsonProperty("repository")
    private Repository repository;

}

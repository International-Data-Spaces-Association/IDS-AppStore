package de.fraunhofer.fit.appstore.model.registry.webhook;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * The event entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event implements Serializable {

    /**
     * Default serial version uid.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The event type.
     */
    @JsonProperty("type")
    private EventType type;

    /**
     * When the event occurs.
     */
    @JsonProperty("occur_at")
    private String occurAt;

    /**
     * The operator.
     */
    @JsonProperty("operator")
    private String operator;

    /**
     * The event data.
     */
    @JsonProperty("event_data")
    private EventData eventData;
}

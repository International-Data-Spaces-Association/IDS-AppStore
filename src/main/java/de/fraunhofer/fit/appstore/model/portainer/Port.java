package de.fraunhofer.fit.appstore.model.portainer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Entity for mapping port attributes.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Port implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The port's protocol.
     */
    private String protocol;

    /**
     * The port's number.
     */
    private int number;

    /**
     * The port's label.
     */
    private String label;

    /**
     * A list of ports.
     */
    private List<Port> portList;

    /**
     * Building string from port attributes.
     *
     * @return The string.
     */
    public String createPortStr() {
        return String.format("%s:%s/%s", getNumber(), getNumber(), getProtocol());
    }
}

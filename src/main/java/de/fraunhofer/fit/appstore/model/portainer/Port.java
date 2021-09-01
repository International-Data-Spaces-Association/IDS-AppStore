/*
 * Copyright 2021 Fraunhofer Institute for Applied Information Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

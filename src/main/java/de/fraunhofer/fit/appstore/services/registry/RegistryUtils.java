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
package de.fraunhofer.fit.appstore.services.registry;

import com.github.curiousoddman.rgxgen.RgxGen;

/**
 * Class with utilities for the registry service.
 */
public final class RegistryUtils {

    private RegistryUtils() {
        // not used
    }

    /**
     * Create username.
     *
     * @return The username.
     */
    public static String createLowercaseUsername() {
        return new RgxGen("(([a-z]{4})([0-9]{2}))").generate();
    }

    /**
     * Create valid password.
     *
     * @return The password.
     */
    public static String createValidPassword() {
        return new RgxGen("(([0-9]{2})([A-Z]{2})([a-z]{3})([!$@&?+-]{1}))").generate();
    }
}

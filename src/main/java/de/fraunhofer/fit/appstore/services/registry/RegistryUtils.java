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

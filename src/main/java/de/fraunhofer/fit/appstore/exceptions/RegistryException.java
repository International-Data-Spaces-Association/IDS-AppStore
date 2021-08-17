package de.fraunhofer.fit.appstore.exceptions;

/**
 * Thrown if some registry processing failed.
 */
public class RegistryException extends RuntimeException {

    /**
     * Default serial version uid.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Construct a RegistryException with the specified detail message.
     *
     * @param msg The detail message.
     */
    public RegistryException(final String msg) {
        super(msg);
    }


    /**
     * Construct a RegistryException with the specified detail message and cause.
     *
     * @param msg   The detail message.
     * @param cause The cause.
     */
    public RegistryException(final String msg, final Throwable cause) {
        super(msg, cause);
    }
}

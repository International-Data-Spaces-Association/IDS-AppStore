package de.fraunhofer.fit.appstore.exceptions;

/**
 * Thrown if a webhook cannot be processed.
 */
public class WebhookException extends RuntimeException {

    /**
     * Default serial version uid.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Construct a WebhookException with the specified detail message.
     *
     * @param msg The detail message.
     */
    public WebhookException(final String msg) {
        super(msg);
    }

    /**
     * Construct a WebhookException with the specified detail message and cause.
     *
     * @param msg   The detail message.
     * @param cause The cause.
     */
    public WebhookException(final String msg, final Throwable cause) {
        super(msg, cause);
    }
}

package de.fraunhofer.fit.appstore.exceptions;

/**
 * Thrown if some template exception occurs.
 */
public class TemplateException extends Exception {

    /**
     * Default serial version uid.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Construct a WebhookException with the specified detail message.
     *
     * @param msg The detail message.
     */
    public TemplateException(final String msg) {
        super(msg);
    }

    /**
     * Construct a WebhookException with the specified detail message and cause.
     *
     * @param msg   The detail message.
     * @param cause The cause.
     */
    public TemplateException(final String msg, final Throwable cause) {
        super(msg, cause);
    }
}

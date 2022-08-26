/*
 * Copyright 2020-2022 Fraunhofer Institute for Software and Systems Engineering
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
package io.dataspaceconnector.common.exception;

/**
 * Thrown to indicate that a problem with a message response occurred.
 */
public class MessageResponseException extends RuntimeException {
    /**
     * Default serial version uid.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Construct a MessageResponseException with the cause.
     *
     * @param cause The cause.
     */
    public MessageResponseException(final Throwable cause) {
        super(cause);
    }

    /**
     * Construct a MessageResponseException with the specified detail message and cause.
     *
     * @param msg The detail message.
     */
    public MessageResponseException(final String msg) {
        super(msg);
    }

    /**
     * Construct a MessageResponseException with the specified detail message and cause.
     *
     * @param msg   The detail message.
     * @param cause The cause.
     */
    public MessageResponseException(final String msg, final Throwable cause) {
        super(msg, cause);
    }

    /**
     * Construct a MessageResponseException with the specified detail message and cause.
     *
     * @param msg   The detail message.
     * @param cause The cause.
     */
    public MessageResponseException(final ErrorMessage msg, final Throwable cause) {
        super(msg.toString(), cause);
    }
}

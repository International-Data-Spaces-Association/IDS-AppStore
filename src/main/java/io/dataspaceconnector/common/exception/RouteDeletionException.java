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
 * Thrown to indicate that an error occurred while trying to delete a Camel route.
 */
public class RouteDeletionException extends RuntimeException {
    private static final long serialVersionUID = 42L;
    /**
     * Constructs a RouteDeletionException with the specified message and cause.
     *
     * @param msg the message.
     * @param cause the cause.
     */
    public RouteDeletionException(final String msg, final Throwable cause) {
        super(msg, cause);
    }

}

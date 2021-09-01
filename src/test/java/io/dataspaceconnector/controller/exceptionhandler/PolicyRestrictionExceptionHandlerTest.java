/*
 * Copyright 2020 Fraunhofer Institute for Software and Systems Engineering
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
package io.dataspaceconnector.controller.exceptionhandler;

import io.dataspaceconnector.common.exception.ErrorMessage;
import io.dataspaceconnector.common.exception.PolicyRestrictionException;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PolicyRestrictionExceptionHandlerTest {

    private PolicyRestrictionExceptionHandler handler = new PolicyRestrictionExceptionHandler();

    @Test
    public void handlePolicyRestrictionException_anyException_returnForbiddenError() {
        /* ARRANGE */
        final var exception = new PolicyRestrictionException(ErrorMessage.POLICY_RESTRICTION);

        /* ACT */
        final var result = handler.handlePolicyRestrictionException(exception);

        /* ASSERT */
        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
    }

    @Test
    public void handlePolicyRestrictionException_anyException_returnJsonContentType() {
        /* ARRANGE */
        final var exception = new PolicyRestrictionException(ErrorMessage.POLICY_RESTRICTION);

        /* ACT */
        final var result = handler.handlePolicyRestrictionException(exception);

        /* ASSERT */
        assertEquals(MediaType.APPLICATION_JSON, result.getHeaders().getContentType());
    }

    @Test
    public void handlePolicyRestrictionException_anyException_returnJsonObject() {
        /* ARRANGE */
        final var body = new JSONObject();
        body.put("message", "A policy restriction has been detected.");

        final var exception = new PolicyRestrictionException(ErrorMessage.POLICY_RESTRICTION);

        /* ACT */
        final var result = handler.handlePolicyRestrictionException(exception);

        /* ASSERT */
        assertEquals(body, result.getBody());
    }

    @Test
    public void handlePolicyRestrictionException_null_returnJsonObject() {
        /* ARRANGE */
        final var body = new JSONObject();
        body.put("message", "A policy restriction has been detected.");

        /* ACT */
        final var result = handler.handlePolicyRestrictionException(null);

        /* ASSERT */
        assertEquals(body, result.getBody());
    }
}

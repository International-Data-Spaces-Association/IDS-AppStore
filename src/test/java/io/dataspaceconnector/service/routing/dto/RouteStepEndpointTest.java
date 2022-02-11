/*
 * Copyright 2020 Fraunhofer Institute for Software and Systems Engineering
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
package io.dataspaceconnector.service.routing.dto;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RouteStepEndpointTest {

    @Test
    void create_validRouteStepEndpoint_returnNew() {
        /* ARRANGE */
        final var accessUrl = "https://appstore.com";

        /* ACT */
        final var routeStepEndpoint = new RouteStepEndpoint(accessUrl, HttpMethod.GET);

        /* ASSERT */
        assertEquals(accessUrl, routeStepEndpoint.getEndpointUrl());
        assertEquals(HttpMethod.GET, routeStepEndpoint.getHttpMethod());
    }

}

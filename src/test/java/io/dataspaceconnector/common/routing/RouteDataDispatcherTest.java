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
package io.dataspaceconnector.common.routing;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import io.dataspaceconnector.common.exception.DataDispatchException;
import org.apache.camel.Exchange;
import org.apache.camel.ExtendedCamelContext;
import org.apache.camel.Message;
import org.apache.camel.ProducerTemplate;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {RouteDataDispatcher.class})
class RouteDataDispatcherTest {

    @Mock
    private Exchange exchange;

    @Mock
    private Message in;

    @MockBean
    private ExtendedCamelContext camelContext;

    @MockBean
    private ProducerTemplate producerTemplate;

    @Autowired
    private RouteDataDispatcher dispatcher;

    private final URI routeId = URI.create("https://" + UUID.randomUUID());

    final byte[] data = "data".getBytes(StandardCharsets.UTF_8);

    @Test
    void send_noExceptionInRoute_sendData() {
        /* ARRANGE */
        when(producerTemplate.send(anyString(), any(Exchange.class))).thenReturn(exchange);
        when(exchange.getIn()).thenReturn(in);
        when(exchange.getException()).thenReturn(null);

        /* ACT && ASSERT */
        assertDoesNotThrow(() -> dispatcher.send(routeId, data));
    }

    @Test
    void send_exceptionInRoute_throwDataDispatchException() {
        /* ARRANGE */
        when(producerTemplate.send(anyString(), any(Exchange.class))).thenReturn(exchange);
        when(exchange.getIn()).thenReturn(in);
        when(exchange.getException()).thenReturn(new IllegalArgumentException());

        /* ACT && ASSERT */
        assertThrows(DataDispatchException.class, () -> dispatcher.send(routeId, data));
    }

}

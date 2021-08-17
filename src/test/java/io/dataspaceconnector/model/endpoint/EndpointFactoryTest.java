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
package io.dataspaceconnector.model.endpoint;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest(classes = {EndpointFactory.class})
class EndpointFactoryTest {

    @Autowired
    private EndpointFactory factory;

    @Test
    void create_validDesc_returnNew() {
        /* ARRANGE */
        final var desc = new EndpointDesc();

        /* ACT */
        final var result = factory.create(desc);

        /* ASSERT */
        assertNotNull(result);
    }

    @Test
    public void update_callEndpointFactory_willUpdate() {
        /* ARRANGE */
        final var endpoint = new Endpoint();
        final var desc = new EndpointDesc();

        /* ACT */
        assertTrue(factory.update(endpoint, desc));

        /* ASSERT */
        Mockito.verify(factory, Mockito.atLeastOnce()).updateInternal(eq(endpoint), eq(desc));
    }

    @Test
    public void update_callConnectorEndpointFactory_willNotUpdate() {
        /* ARRANGE */
        final var endpoint = factory.create(new EndpointDesc());
        final var desc = new EndpointDesc();

        /* ACT */
        assertFalse(factory.update(endpoint, desc));

        /* ASSERT */
        Mockito.verify(factory, Mockito.atLeastOnce()).updateInternal(eq(endpoint), eq(desc));
    }

    @Test
    public void update_callGenericEndpointFactory_andWillUpdate() {
        /* ARRANGE */
        final var endpoint = new Endpoint();
        final var desc = new EndpointDesc();

        /* ACT */
        assertTrue(factory.update(endpoint, desc));

        /* ASSERT */
        Mockito.verify(factory, Mockito.atLeastOnce()).updateInternal(eq(endpoint), eq(desc));
    }

    @Test
    public void update_callGenericEndpointFactory_andWillNotUpdate() {
        /* ARRANGE */
        final var endpoint = factory.create(new EndpointDesc());
        final var desc = new EndpointDesc();

        /* ACT */
        assertFalse(factory.update(endpoint, desc));

        /* ASSERT */
        Mockito.verify(factory, Mockito.atLeastOnce()).updateInternal(eq(endpoint), eq(desc));
    }
}

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
package io.dataspaceconnector.controller;

import de.fraunhofer.iais.eis.AppStore;
import de.fraunhofer.iais.eis.AppStoreBuilder;
import de.fraunhofer.iais.eis.BaseConnector;
import de.fraunhofer.iais.eis.ConnectorEndpointBuilder;
import de.fraunhofer.iais.eis.SecurityProfile;
import de.fraunhofer.iais.eis.ids.jsonld.Serializer;
import io.dataspaceconnector.common.ids.ConnectorService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class MainControllerIT {

    @MockBean
    ConnectorService connectorService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void getPublicSelfDescription_nothing_returnValidDescription() throws Exception {
        /* ARRANGE */
        final var connector = getConnectorWithoutResources();
        Mockito.when(connectorService.getAppStoreWithoutResources()).thenReturn(connector);

        /* ACT */
        final var result = mockMvc.perform(get("/public")).andExpect(status().isOk()).andReturn();

        /* ASSERT */
        assertDoesNotThrow(() -> new Serializer().deserialize(result.getResponse().getContentAsString(), AppStore.class));
        assertEquals(connector.toRdf(), result.getResponse().getContentAsString());
    }

    /***********************************************************************************************
     * Utilities.                                                                                  *
     **********************************************************************************************/

    private AppStore getConnectorWithoutResources() {
        return new AppStoreBuilder()
                ._curator_(URI.create("https://someBody"))
                ._maintainer_(URI.create("https://someoneElse"))
                ._outboundModelVersion_("4.0.0")
                ._inboundModelVersion_(de.fraunhofer.iais.eis.util.Util.asList("4.0.0"))
                ._securityProfile_(SecurityProfile.BASE_SECURITY_PROFILE)
                ._hasDefaultEndpoint_(new ConnectorEndpointBuilder()
                        ._accessURL_(URI.create("https://accessUrl"))
                        .build())
                .build();
    }
}

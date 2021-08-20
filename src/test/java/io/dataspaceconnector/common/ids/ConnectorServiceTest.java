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
package io.dataspaceconnector.common.ids;

import de.fraunhofer.iais.eis.BaseConnectorBuilder;
import de.fraunhofer.iais.eis.ConfigurationModel;
import de.fraunhofer.iais.eis.ConfigurationModelBuilder;
import de.fraunhofer.iais.eis.ConfigurationModelImpl;
import de.fraunhofer.iais.eis.Connector;
import de.fraunhofer.iais.eis.ConnectorDeployMode;
import de.fraunhofer.iais.eis.ConnectorEndpointBuilder;
import de.fraunhofer.iais.eis.ConnectorStatus;
import de.fraunhofer.iais.eis.KeyType;
import de.fraunhofer.iais.eis.LogLevel;
import de.fraunhofer.iais.eis.PublicKeyBuilder;
import de.fraunhofer.iais.eis.ResourceBuilder;
import de.fraunhofer.iais.eis.ResourceCatalog;
import de.fraunhofer.iais.eis.ResourceCatalogBuilder;
import de.fraunhofer.iais.eis.SecurityProfile;
import de.fraunhofer.iais.eis.util.TypedLiteral;
import de.fraunhofer.iais.eis.util.Util;
import de.fraunhofer.ids.messaging.core.config.ConfigContainer;
import de.fraunhofer.ids.messaging.core.daps.DapsTokenProvider;
import io.dataspaceconnector.model.catalog.Catalog;
import io.dataspaceconnector.model.resource.Resource;
import io.dataspaceconnector.service.resource.ids.builder.IdsCatalogBuilder;
import io.dataspaceconnector.service.resource.ids.builder.IdsResourceBuilder;
import io.dataspaceconnector.service.resource.type.CatalogService;
import io.dataspaceconnector.service.resource.type.ResourceService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {ConnectorService.class})
public class ConnectorServiceTest {

    @MockBean
    private DeserializationService deserializationService;

    @MockBean
    private ConfigContainer configContainer;

    @MockBean
    private DapsTokenProvider tokenProvider;

    @MockBean
    private CatalogService catalogService;

    @MockBean
    private IdsCatalogBuilder catalogBuilder;

    @MockBean
    private IdsResourceBuilder resourceBuilder;

    @MockBean
    private ResourceService resourceService;

    @Autowired
    private ConnectorService connectorService;

    @Test
    public void getConnectorWithOfferedResources_returnConnectorWithCatalog() {
        /* ARRANGE */
        final var connector = getConnector();
        final var catalog = getCatalog();
        final var idsCatalog = getIdsCatalog();

        when(configContainer.getConnector()).thenReturn(connector);
        when(catalogService.getAll(Pageable.unpaged())).thenReturn(new PageImpl<>(List.of(catalog)));
        when(catalogBuilder.create(eq(catalog), eq(0))).thenReturn(idsCatalog);

        /* ACT */
        final var result = connectorService.getAppStoreWithAppResources();

        /* ASSERT */
        assertEquals(connector.getId(), result.getId());
        assertEquals(connector.getOutboundModelVersion(), result.getOutboundModelVersion());
        assertEquals(connector.getInboundModelVersion(), result.getInboundModelVersion());
        assertEquals(connector.getVersion(), result.getVersion());
        assertEquals(connector.getTitle(), result.getTitle());
        assertEquals(connector.getPublicKey(), result.getPublicKey());
        assertEquals(connector.getCurator(), result.getCurator());
        assertEquals(connector.getMaintainer(), result.getMaintainer());
        assertEquals(connector.getDescription(), result.getDescription());
        assertEquals(connector.getSecurityProfile(), result.getSecurityProfile());
        assertEquals(connector.getHasDefaultEndpoint(), result.getHasDefaultEndpoint());

        assertFalse(result.getResourceCatalog().isEmpty());
        assertEquals(1, result.getResourceCatalog().size());
        assertEquals(idsCatalog, result.getResourceCatalog().get(0));
    }

    @Test
    public void getConnectorWithoutResources_returnConnectorWithoutCatalog() {
        /* ARRANGE */
        final var connector = getConnector();

        when(configContainer.getConnector()).thenReturn(connector);

        /* ACT */
        final var result = connectorService.getAppStoreWithoutResources();

        /* ASSERT */
        assertEquals(connector.getId(), result.getId());
        assertEquals(connector.getOutboundModelVersion(), result.getOutboundModelVersion());
        assertEquals(connector.getInboundModelVersion(), result.getInboundModelVersion());
        assertEquals(connector.getVersion(), result.getVersion());
        assertEquals(connector.getTitle(), result.getTitle());
        assertEquals(connector.getPublicKey(), result.getPublicKey());
        assertEquals(connector.getCurator(), result.getCurator());
        assertEquals(connector.getMaintainer(), result.getMaintainer());
        assertEquals(connector.getDescription(), result.getDescription());
        assertEquals(connector.getSecurityProfile(), result.getSecurityProfile());
        assertEquals(connector.getHasDefaultEndpoint(), result.getHasDefaultEndpoint());

        assertNull(result.getResourceCatalog());
    }

    @Test
    @SneakyThrows
    public void updateConfigModel_noErrorDuringUpdate_updateConfiguration() {
        /* ARRANGE */
        final var catalog = getCatalog();
        final var idsCatalog = getIdsCatalog();
        final var configModel = getConfigModel();

        when(configContainer.getConnector()).thenReturn(getConnector());
        when(catalogService.getAll(Pageable.unpaged())).thenReturn(new PageImpl<>(List.of(catalog)));
        when(catalogBuilder.create(catalog, 0)).thenReturn(idsCatalog);
        when(configContainer.getConfigurationModel()).thenReturn(configModel);
        doNothing().when(configContainer).updateConfiguration(any());

        /* ACT */
        connectorService.updateConfigModel();

        /* ASSERT */
        final var connector = connectorService.getAppStoreWithAppResources();
        final var configModelImpl = ((ConfigurationModelImpl) configModel);
        configModelImpl.setConnectorDescription(connector);

        verify(configContainer, times(1)).updateConfiguration(configModelImpl);
    }

    @Test
    public void getOfferedResourceById_inputNull_throwNullPointerException() {
        /* ACT && ASSERT */
        assertThrows(NullPointerException.class,
                () -> connectorService.getOfferedResourceById(null));
    }

    @Test
    public void getOfferedResourceById_resourcePresent_returnResource() {
        /* ARRANGE */
        final var uuid = UUID.randomUUID();
        final var uri = URI.create("https://resource-id.com/" + uuid);
        final var resource = getResource(uuid);
        final var idsResource = getIdsResource();

        when(resourceService.getAll(Pageable.unpaged()))
                .thenReturn(new PageImpl<>(List.of(resource)));
        when(resourceBuilder.create(resource)).thenReturn(idsResource);

        /* ACT */
        final var result = connectorService.getOfferedResourceById(uri);

        /* ASSERT */
        assertTrue(result.isPresent());
        assertEquals(idsResource, result.get());
    }

    @Test
    public void getOfferedResourceById_resourceNotPresent_returnEmptyOptional() {
        /* ARRANGE */
        final var uuid = UUID.randomUUID();
        final var uri = URI.create("https://resource-id.com/" + uuid);
        final var resource = getResource(UUID.randomUUID());

        when(resourceService.getAll(Pageable.unpaged()))
                .thenReturn(new PageImpl<>(List.of(resource)));

        /* ACT */
        final var result = connectorService.getOfferedResourceById(uri);

        /* ASSERT */
        assertTrue(result.isEmpty());
    }

    /**************************************************************************
     * Utilities.
     *************************************************************************/

    private Connector getConnector() {
        return new BaseConnectorBuilder(URI.create("https://connector-id.com"))
                ._maintainer_(URI.create("https://example.com"))
                ._curator_(URI.create("https://example.com"))
                ._securityProfile_(SecurityProfile.BASE_SECURITY_PROFILE)
                ._outboundModelVersion_("4.0.0")
                ._inboundModelVersion_(Util.asList("4.0.0"))
                ._title_(Util.asList(new TypedLiteral("Dataspace Connector")))
                ._description_(Util.asList(new TypedLiteral(
                        "Test Connector")))
                ._version_("v3.0.0")
                ._publicKey_(new PublicKeyBuilder()
                        ._keyType_(KeyType.RSA)
                        ._keyValue_("something".getBytes())
                        .build()
                )
                ._hasDefaultEndpoint_(new ConnectorEndpointBuilder()
                        ._accessURL_(URI.create("/api/ids/data"))
                        .build())
                .build();
    }

    private Catalog getCatalog() {
        return new Catalog();
    }

    private ResourceCatalog getIdsCatalog() {
        return new ResourceCatalogBuilder().build();
    }

    private ConfigurationModel getConfigModel() {
        return new ConfigurationModelBuilder(URI.create("https://w3id" +
                ".org/idsa/autogen/configModel/462e5a6a-7143-4453-9c5c-d2aba8c9aec1"))
                ._configurationModelLogLevel_(LogLevel.NO_LOGGING)
                ._connectorDeployMode_(ConnectorDeployMode.TEST_DEPLOYMENT)
                ._connectorStatus_(ConnectorStatus.CONNECTOR_ONLINE)
                ._connectorDescription_(new BaseConnectorBuilder()
                        ._maintainer_(URI.create("https://example.com"))
                        ._curator_(URI.create("https://example.com"))
                        ._securityProfile_(SecurityProfile.BASE_SECURITY_PROFILE)
                        ._outboundModelVersion_("4.0.0")
                        ._inboundModelVersion_(Util.asList("4.0.0"))
                        ._title_(Util.asList(new TypedLiteral("Dataspace Connector")))
                        ._description_(Util.asList(new TypedLiteral(
                                "Test Connector")))
                        ._version_("v3.0.0")
                        ._publicKey_(new PublicKeyBuilder()
                                ._keyType_(KeyType.RSA)
                                ._keyValue_("something".getBytes())
                                .build()
                        )
                        ._hasDefaultEndpoint_(new ConnectorEndpointBuilder()
                                ._accessURL_(URI.create("/api/ids/data"))
                                .build())
                        .build())
                .build();
    }

    @SneakyThrows
    private Resource getResource(final UUID id) {
        final var resourceConstructor = Resource.class.getDeclaredConstructor();
        resourceConstructor.setAccessible(true);

        final var resource = resourceConstructor.newInstance();
        ReflectionTestUtils.setField(resource, "id", id);
        return resource;
    }

    private de.fraunhofer.iais.eis.Resource getIdsResource() {
        return new ResourceBuilder(URI.create("https://resource-id.com")).build();
    }

}

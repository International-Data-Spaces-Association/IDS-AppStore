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
package io.dataspaceconnector.service.resource.type;

import de.fraunhofer.iais.eis.ConfigurationModel;
import ids.messaging.core.config.ConfigContainer;
import ids.messaging.core.config.ConfigProperties;
import ids.messaging.core.config.ConfigUpdateException;
import io.dataspaceconnector.common.runtime.ServiceResolver;
import io.dataspaceconnector.model.auth.AuthenticationDesc;
import io.dataspaceconnector.model.base.AbstractFactory;
import io.dataspaceconnector.model.configuration.Configuration;
import io.dataspaceconnector.model.configuration.ConfigurationDesc;
import io.dataspaceconnector.model.keystore.KeystoreDesc;
import io.dataspaceconnector.model.proxy.ProxyDesc;
import io.dataspaceconnector.model.truststore.TruststoreDesc;
import io.dataspaceconnector.repository.BaseEntityRepository;
import io.dataspaceconnector.repository.ConfigurationRepository;
import io.dataspaceconnector.service.resource.base.BaseEntityService;
import io.dataspaceconnector.service.resource.ids.builder.IdsConfigModelBuilder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.util.Optional;
import java.util.UUID;

/**
 * Service class for the configuration.
 */
@Getter(AccessLevel.PACKAGE)
@Setter(AccessLevel.NONE)
@Log4j2
public class ConfigurationService extends BaseEntityService<Configuration, ConfigurationDesc> {

    /**
     * The current context.
     */
    private final @NonNull ServiceResolver svcResolver;

    /**
     * Builds the ids config.
     */
    private final @NonNull IdsConfigModelBuilder configBuilder;

    /**
     * Constructor.
     *
     * @param repository       The configuration repository.
     * @param factory          The configuration logic.
     * @param resolver         The application context.
     * @param idsConfigBuilder The ids Config model builder.
     */
    public ConfigurationService(final BaseEntityRepository<Configuration> repository,
                                final AbstractFactory<Configuration, ConfigurationDesc> factory,
                                final @NonNull ServiceResolver resolver,
                                final @NonNull IdsConfigModelBuilder idsConfigBuilder) {
        super(repository, factory);
        this.svcResolver = resolver;
        this.configBuilder = idsConfigBuilder;
    }

    /**
     * Try to find the active configuration.
     *
     * @return The active configuration if it exists.
     */
    public Optional<Configuration> findActiveConfig() {
        return ((ConfigurationRepository) getRepository()).findActive();
    }

    /**
     * Get the active configuration.
     *
     * @return The active configuration.
     */
    public Configuration getActiveConfig() {
        final var config = findActiveConfig();
        assert config.isPresent();
        return config.get();
    }

    /**
     * Mark a new configuration as active.
     *
     * @param newConfig Id of the new active configuration.
     * @param startup true, if application is currently starting
     */
    public void swapActiveConfig(final UUID newConfig, final boolean startup)
            throws ConfigUpdateException {
        final var configToReplace = findActiveConfig();

        if (configToReplace.isPresent()) {
            swapActiveConfigInDb(newConfig);
            if (!startup) {
                try {
                    resetMessagingConfig();
                } catch (ConfigUpdateException e) {
                    if (log.isWarnEnabled()) {
                        log.warn("Updating the configuration failed, rollback to the latest one. "
                                + "[exception=({})]", e.getMessage());
                    }
                    swapActiveConfigInDb(configToReplace.get().getId());
                    resetMessagingConfig();
                }
            } else {
                final var newActiveConfig = findActiveConfig();
                newActiveConfig.ifPresent(this::updateConfigProperties);
            }
        } else {
            ((ConfigurationRepository) getRepository()).setActive(newConfig);
        }
    }

    /**
     * Mark a new configuration as active.
     *
     * @param newConfig Id of the new active configuration.
     */
    public void swapActiveConfig(final UUID newConfig) throws ConfigUpdateException {
        swapActiveConfig(newConfig, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Configuration update(final UUID entityId, final ConfigurationDesc desc) {
        Configuration config;

        if (getActiveConfig().getId().equals(entityId)) {
            final var oldConfig = super.get(entityId);
            final var oldDesc = fromConfigModel(oldConfig);
            final var validatedDesc = deepValidateKeystores(oldDesc, desc);
            config = super.update(entityId, validatedDesc);

            updateRuntimeConfig(entityId, oldDesc);
        } else {
             config = super.update(entityId, desc);
        }

        return config;
    }

    /**
     * If changes have been made to the active configuration,
     * the runtime configuration must be updated. If the configuration contains errors and
     * cannot be adopted at connector runtime, a rollback to the old configuration is
     * performed at runtime to guarantee the functionality of the connector as a whole.
     *
     * @param entityId The ID of the active configuration in the database.
     * @param oldDesc The old configuration before changes, possible rollback target.
     */
    private void updateRuntimeConfig(final UUID entityId, final ConfigurationDesc oldDesc) {
        if (log.isInfoEnabled()) {
            log.info("Updating active configuration...");
        }
        try {
            resetMessagingConfig();
            if (log.isInfoEnabled()) {
                log.info("Successfully updated active configuration.");
            }
        } catch (ConfigUpdateException e) {
            if (log.isWarnEnabled()) {
                log.warn("Updating active configuration failed, rollback to before changes"
                        + " to protect connector runtime. "
                        + "[exception=({})]", e.getMessage());
            }

            // Rollback.
            super.update(entityId, oldDesc);

            try {
                resetMessagingConfig();
            } catch (ConfigUpdateException ignored) {
                // Nothing to do - old config didn't work too, nothing we can do here.
            }
        }
    }

    /**
     * Validates whether the keystore and truststore information is completely present in the new
     * configuration. If not, the information of the old configuration is copied (if present).
     *
     * @param oldDesc The configuration to be updated.
     * @param desc The new configuration.
     * @return New configuration with key- and truststore settings.
     */
    private ConfigurationDesc deepValidateKeystores(final ConfigurationDesc oldDesc,
                                                    final ConfigurationDesc desc) {
        // Do the keystores exist at all in the new config?
        if (desc.getKeystore() == null) {
            desc.setKeystore(new KeystoreDesc());
        }
        if (desc.getTruststore() == null) {
            desc.setTruststore(new TruststoreDesc());
        }


        // Validate keystore settings.
        final var oldKeystore = oldDesc.getKeystore();
        final var newKeystore = desc.getKeystore();

        if (oldKeystore != null) {
            if (newKeystore.getAlias() == null) {
                newKeystore.setAlias(oldKeystore.getAlias());
            }

            if (newKeystore.getPassword() == null) {
                newKeystore.setPassword(oldKeystore.getPassword());
            }

            if (newKeystore.getLocation() == null) {
                newKeystore.setLocation(oldKeystore.getLocation());
            }
        }

        // Validate truststore settings.
        final var oldTruststore = oldDesc.getTruststore();
        final var newTruststore = desc.getTruststore();

        if (oldTruststore != null) {
            if (newTruststore.getPassword() == null) {
                newTruststore.setPassword(oldTruststore.getPassword());
            }

            if (newTruststore.getLocation() == null) {
                newTruststore.setLocation(oldTruststore.getLocation());
            }
        }

        return desc;
    }

    private void swapActiveConfigInDb(final UUID newConfig) {
        final var repo = (ConfigurationRepository) getRepository();
        repo.unsetActive();
        repo.setActive(newConfig);

        if (log.isInfoEnabled()) {
            log.info("Successfully swapped active configuration in database.");
        }
    }

    private void resetMessagingConfig() throws ConfigUpdateException {
        if (log.isDebugEnabled()) {
            log.debug("Updating Messaging Services configuration...");
        }

        final var activeConfig = getActiveConfig();
        final var configuration = configBuilder.create(activeConfig);

        updateConfigProperties(activeConfig);
        updateConfigContainer(configuration, activeConfig);

        if (log.isDebugEnabled()) {
            log.debug("Successfully updated Messaging Services configuration.");
        }
    }

    private void updateConfigContainer(final ConfigurationModel configuration,
                                       final Configuration activeConfig)
            throws ConfigUpdateException {
        final var configContainer = svcResolver.getService(ConfigContainer.class);

        if (configContainer.isPresent()) {
            final var configBean = configContainer.get();

            try {
                updateKeyStoreManager(activeConfig, configBean);
            } catch (Exception e) {
                if (log.isWarnEnabled()) {
                    log.warn("Could not update KeyStoreManager. [exception=({})]",
                            e.getMessage(), e);
                }
                throw new ConfigUpdateException("Could not update KeyStoreManager.", e.getCause());
            }

            configBean.updateConfiguration(configuration);
        }
    }

    private void updateKeyStoreManager(final Configuration activeConfig,
                                       final ConfigContainer configBean)
            throws NoSuchFieldException, IllegalAccessException {
        final var keyStoreManager = configBean.getKeyStoreManager();

        final var keyStorePw = keyStoreManager.getClass().getDeclaredField("keyStorePw");
        keyStorePw.setAccessible(true);
        keyStorePw.set(keyStoreManager, activeConfig.getKeystore().getPassword().toCharArray());

        final var trustStorePw = keyStoreManager.getClass().getDeclaredField("trustStorePw");
        trustStorePw.setAccessible(true);
        trustStorePw.set(keyStoreManager, activeConfig.getTruststore().getPassword().toCharArray());

        final var keyAlias = keyStoreManager.getClass().getDeclaredField("keyAlias");
        keyAlias.setAccessible(true);
        keyAlias.set(keyStoreManager, activeConfig.getKeystore().getAlias());
    }

    private void updateConfigProperties(final Configuration activeConfig) {
        final var configProperties = svcResolver.getService(ConfigProperties.class);
        if (configProperties.isPresent()) {
            final var configBean = configProperties.get();
            configBean.setKeyAlias(activeConfig.getKeystore().getAlias());
            configBean.setKeyStorePassword(activeConfig.getKeystore().getPassword());
            configBean.setTrustStorePassword(activeConfig.getTruststore().getPassword());
        }
    }

    private ConfigurationDesc fromConfigModel(final Configuration configuration) {
        final var desc = new ConfigurationDesc();
        desc.setCurator(configuration.getCurator());
        desc.setConnectorId(configuration.getConnectorId());
        desc.setDeployMode(configuration.getDeployMode());
        desc.setDefaultEndpoint(configuration.getDefaultEndpoint());
        desc.setInboundModelVersion(configuration.getInboundModelVersion());
        desc.setLogLevel(configuration.getLogLevel());
        desc.setMaintainer(configuration.getMaintainer());
        desc.setOutboundModelVersion(configuration.getOutboundModelVersion());
        desc.setSecurityProfile(configuration.getSecurityProfile());
        desc.setStatus(configuration.getStatus());
        desc.setTitle(configuration.getTitle());
        desc.setAdditional(configuration.getAdditional());
        desc.setDescription(configuration.getDescription());

        if (configuration.getKeystore() != null) {
            final var keyStoreDesc = new KeystoreDesc();
            keyStoreDesc.setLocation(configuration.getKeystore().getLocation());
            keyStoreDesc.setAlias(configuration.getKeystore().getAlias());
            keyStoreDesc.setPassword(configuration.getKeystore().getPassword());
            desc.setKeystore(keyStoreDesc);
        }

        if (configuration.getTruststore() != null) {
            final var trustStoreDesc = new TruststoreDesc();
            trustStoreDesc.setAlias(configuration.getTruststore().getAlias());
            trustStoreDesc.setLocation(configuration.getTruststore().getLocation());
            trustStoreDesc.setPassword(configuration.getTruststore().getPassword());
            desc.setTruststore(trustStoreDesc);
        }

        if (configuration.getProxy() != null) {
            final var proxyDesc = new ProxyDesc();
            proxyDesc.setExclusions(configuration.getProxy().getExclusions());
            proxyDesc.setLocation(configuration.getProxy().getLocation());
            if (configuration.getProxy().getAuthentication() != null) {
                final var authDesc = new AuthenticationDesc(
                        configuration.getProxy().getAuthentication().getUsername(),
                        configuration.getProxy().getAuthentication().getPassword()
                );
                proxyDesc.setAuthentication(authDesc);
            }
            desc.setProxy(proxyDesc);
        }
        return desc;
    }
}

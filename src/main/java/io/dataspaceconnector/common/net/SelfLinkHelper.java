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
package io.dataspaceconnector.common.net;

import io.dataspaceconnector.common.exception.ErrorMessage;
import io.dataspaceconnector.common.exception.ResourceNotFoundException;
import io.dataspaceconnector.common.exception.UnreachableLineException;
import io.dataspaceconnector.controller.resource.view.agreement.AgreementViewAssembler;
import io.dataspaceconnector.controller.resource.view.app.AppViewAssembler;
import io.dataspaceconnector.controller.resource.view.artifact.ArtifactViewAssembler;
import io.dataspaceconnector.controller.resource.view.catalog.CatalogViewAssembler;
import io.dataspaceconnector.controller.resource.view.contract.ContractViewAssembler;
import io.dataspaceconnector.controller.resource.view.endpoint.EndpointViewAssembler;
import io.dataspaceconnector.controller.resource.view.representation.RepresentationViewAssembler;
import io.dataspaceconnector.controller.resource.view.resource.ResourceViewAssembler;
import io.dataspaceconnector.controller.resource.view.rule.ContractRuleViewAssembler;
import io.dataspaceconnector.controller.resource.view.subscription.SubscriptionViewAssembler;
import io.dataspaceconnector.controller.resource.view.util.SelfLinking;
import io.dataspaceconnector.model.agreement.Agreement;
import io.dataspaceconnector.model.app.App;
import io.dataspaceconnector.model.artifact.Artifact;
import io.dataspaceconnector.model.base.Entity;
import io.dataspaceconnector.model.catalog.Catalog;
import io.dataspaceconnector.model.contract.Contract;
import io.dataspaceconnector.model.endpoint.Endpoint;
import io.dataspaceconnector.model.representation.Representation;
import io.dataspaceconnector.model.resource.Resource;
import io.dataspaceconnector.model.rule.ContractRule;
import io.dataspaceconnector.model.subscription.Subscription;

import java.net.URI;

/**
 * This is a helper class for retrieving self-links of a database entity.
 */
public final class SelfLinkHelper {
    /**
     * View assembler for catalogs.
     */
    private static final CatalogViewAssembler CATALOG_ASSEMBLER = new CatalogViewAssembler();

    /**
     * View assembler for resources.
     */
    private static final ResourceViewAssembler RESOURCE_ASSEMBLER = new ResourceViewAssembler();

    /**
     * View assembler for representations.
     */
    private static final RepresentationViewAssembler REPRESENTATION_ASSEMBLER =
            new RepresentationViewAssembler();

    /**
     * View assembler for artifacts.
     */
    private static final ArtifactViewAssembler ARTIFACT_ASSEMBLER = new ArtifactViewAssembler();

    /**
     * View assembler for contracts.
     */
    private static final ContractViewAssembler CONTRACT_ASSEMBLER = new ContractViewAssembler();

    /**
     * View assembler for contract rules.
     */
    private static final ContractRuleViewAssembler RULE_ASSEMBLER = new ContractRuleViewAssembler();

    /**
     * View assembler for contract agreements.
     */
    private static final AgreementViewAssembler AGREEMENT_ASSEMBLER = new AgreementViewAssembler();

    /**
     * View assembler for subscriptions.
     */
    private static final SubscriptionViewAssembler SUBSCRIPTION_ASSEMBLER =
            new SubscriptionViewAssembler();

    /**
     * View assembler for endpoints.
     */
    private static final EndpointViewAssembler ENDPOINT_ASSEMBLER = new EndpointViewAssembler();

    /**
     * View assembler for apps.
     */
    private static final AppViewAssembler APP_ASSEMBLER = new AppViewAssembler();

    /**
     * Default constructor.
     */
    private SelfLinkHelper() {
        // not used
    }

    /**
     * This function is a helper function for hiding the problem that the self-link is always
     * received through the concrete assembler.
     *
     * @param entity The entity.
     * @param <T>    Generic type of database entity.
     * @return The abstract entity.
     */
    public static <T extends Entity> URI getSelfLink(final T entity) {
        if (entity instanceof Catalog) {
            return getSelfLink((Catalog) entity);
        } else if (entity instanceof Resource) {
            return getSelfLink((Resource) entity);
        } else if (entity instanceof Representation) {
            return getSelfLink((Representation) entity);
        } else if (entity instanceof Artifact) {
            return getSelfLink((Artifact) entity);
        } else if (entity instanceof Contract) {
            return getSelfLink((Contract) entity);
        } else if (entity instanceof ContractRule) {
            return getSelfLink((ContractRule) entity);
        } else if (entity instanceof Agreement) {
            return getSelfLink((Agreement) entity);
        } else if (entity instanceof Subscription) {
            return getSelfLink((Subscription) entity);
        } else if (entity instanceof App) {
            return getSelfLink((App) entity);
        } else if (entity instanceof Endpoint) {
            return getSelfLink((Endpoint) entity);
        }

        throw new UnreachableLineException(ErrorMessage.UNKNOWN_TYPE);
    }

    /**
     * Get self-link from abstract entity.
     *
     * @param entity    The entity.
     * @param describer The entity view assembler.
     * @param <T>       The type of the entity.
     * @param <S>       The type of the assembler.
     * @return The abstract entity and its self-link.
     * @throws ResourceNotFoundException If the entity could not be found.
     */
    public static <T extends Entity, S extends SelfLinking> URI getSelfLink(
            final T entity, final S describer) throws ResourceNotFoundException {
        try {
            return describer.getSelfLink(entity.getId()).toUri();
        } catch (IllegalStateException exception) {
            throw new ResourceNotFoundException(ErrorMessage.EMTPY_ENTITY.toString(), exception);
        }
    }

    /**
     * Get self-link of catalog.
     *
     * @param catalog The catalog.
     * @return The self-link of the catalog.
     * @throws ResourceNotFoundException If the resource could not be loaded.
     */
    public static URI getSelfLink(final Catalog catalog) throws ResourceNotFoundException {
        return getSelfLink(catalog, CATALOG_ASSEMBLER);
    }

    /**
     * Get self-link of resource.
     *
     * @param resource The resource.
     * @return The self-link of the resource.
     * @throws ResourceNotFoundException If the resource could not be loaded.
     */
    public static URI getSelfLink(final Resource resource) throws ResourceNotFoundException {
        return getSelfLink(resource, RESOURCE_ASSEMBLER);
    }

    /**
     * Get self-link of representation.
     *
     * @param representation The representation.
     * @return The self-link of the representation.
     * @throws ResourceNotFoundException If the resource could not be loaded.
     */
    public static URI getSelfLink(final Representation representation)
            throws ResourceNotFoundException {
        return getSelfLink(representation, REPRESENTATION_ASSEMBLER);
    }

    /**
     * Get self-link of artifact.
     *
     * @param artifact The artifact.
     * @return The self-link of the artifact.
     * @throws ResourceNotFoundException If the resource could not be loaded.
     */
    public static URI getSelfLink(final Artifact artifact) throws ResourceNotFoundException {
        return getSelfLink(artifact, ARTIFACT_ASSEMBLER);
    }

    /**
     * Get self-link of contract.
     *
     * @param contract The contract.
     * @return The self-link of the contract.
     * @throws ResourceNotFoundException If the resource could not be loaded.
     */
    public static URI getSelfLink(final Contract contract) throws ResourceNotFoundException {
        return getSelfLink(contract, CONTRACT_ASSEMBLER);
    }

    /**
     * Get self-link of rule.
     *
     * @param rule The rule.
     * @return The self-link of the rule.
     * @throws ResourceNotFoundException If the resource could not be loaded.
     */
    public static URI getSelfLink(final ContractRule rule) throws ResourceNotFoundException {
        return getSelfLink(rule, RULE_ASSEMBLER);
    }

    /**
     * Get self-link of agreement.
     *
     * @param agreement The agreement.
     * @return The self-link of the agreement.
     * @throws ResourceNotFoundException If the resource could not be loaded.
     */
    public static URI getSelfLink(final Agreement agreement) throws ResourceNotFoundException {
        return getSelfLink(agreement, AGREEMENT_ASSEMBLER);
    }

    /**
     * Get self-link of subscription.
     *
     * @param subscription The subscription.
     * @return The self-link of the subscription.
     * @throws ResourceNotFoundException If the resource could not be loaded.
     */
    public static URI getSelfLink(final Subscription subscription)
            throws ResourceNotFoundException {
        return getSelfLink(subscription, SUBSCRIPTION_ASSEMBLER);
    }

    /**
     * Get self-link of representation.
     * @param app The app.
     * @return The self-link of the app.
     * @throws ResourceNotFoundException If the app could not be loaded.
     */
    public static URI getSelfLink(final App app) throws ResourceNotFoundException {
        return getSelfLink(app, APP_ASSEMBLER);
    }

    /**
     * Get self-link of representation.
     * @param endpoint The endpoint.
     * @return The self-link of the endpoint.
     * @throws ResourceNotFoundException If the endpoint could not be loaded.
     */
    public static URI getSelfLink(final Endpoint endpoint) throws ResourceNotFoundException {
        return getSelfLink(endpoint, ENDPOINT_ASSEMBLER);
    }
}

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
package io.dataspaceconnector.service.resource;

import io.dataspaceconnector.common.exception.ErrorMessage;
import io.dataspaceconnector.common.util.Utils;
import io.dataspaceconnector.model.artifact.Artifact;
import io.dataspaceconnector.model.catalog.Catalog;
import io.dataspaceconnector.model.contract.Contract;
import io.dataspaceconnector.model.representation.Representation;
import io.dataspaceconnector.model.resource.Resource;
import io.dataspaceconnector.model.rule.ContractRule;
import io.dataspaceconnector.model.template.ArtifactTemplate;
import io.dataspaceconnector.model.template.CatalogTemplate;
import io.dataspaceconnector.model.template.ContractTemplate;
import io.dataspaceconnector.model.template.RepresentationTemplate;
import io.dataspaceconnector.model.template.ResourceTemplate;
import io.dataspaceconnector.model.template.RuleTemplate;
import io.dataspaceconnector.service.resource.base.RemoteResolver;
import io.dataspaceconnector.service.resource.relation.CatalogResourceLinker;
import io.dataspaceconnector.service.resource.relation.ContractRuleLinker;
import io.dataspaceconnector.service.resource.relation.RepresentationArtifactLinker;
import io.dataspaceconnector.service.resource.relation.ResourceContractLinker;
import io.dataspaceconnector.service.resource.relation.ResourceRepresentationLinker;
import io.dataspaceconnector.service.resource.type.ArtifactService;
import io.dataspaceconnector.service.resource.type.CatalogService;
import io.dataspaceconnector.service.resource.type.ContractService;
import io.dataspaceconnector.service.resource.type.RepresentationService;
import io.dataspaceconnector.service.resource.type.ResourceService;
import io.dataspaceconnector.service.resource.type.RuleService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

/**
 * Builds and links entities from templates.
 */
@RequiredArgsConstructor
@Transactional
@Service
public class TemplateBuilder {

    /**
     * Spring application context.
     */
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * The service for resources.
     */
    private final @NonNull ResourceService resourceService;

    /**
     * The linker for resource-representation relations.
     */
    private final @NonNull ResourceRepresentationLinker resourceRepresentationLinker;

    /**
     * The linker for resource-contract relations.
     */
    private final @NonNull ResourceContractLinker resourceContractLinker;

    /**
     * The service for representations.
     */
    private final @NonNull RepresentationService representationService;

    /**
     * The linker for representation-artifact relations.
     */
    private final @NonNull RepresentationArtifactLinker representationArtifactLinker;

    /**
     * The service for contracts.
     */
    private final @NonNull ContractService contractService;

    /**
     * The service for catalogs.
     */
    private final @NonNull CatalogService catalogService;

    /**
     * The linker for contract-rule relations.
     */
    private final @NonNull ContractRuleLinker contractRuleLinker;

    /**
     * The service for artifacts.
     */
    private final @NonNull ArtifactService artifactService;

    /**
     * The service for rules.
     */
    private final @NonNull RuleService ruleService;

    /**
     * The linker for catalog-resource relations.
     */
    private final @NonNull CatalogResourceLinker catalogResourceLinker;

    /**
     * Build a catalog and dependencies from a template.
     *
     * @param template The catalog template.
     * @return The new resource.
     * @throws IllegalArgumentException if the passed template is null.
     */
    public Catalog build(final CatalogTemplate template) {
        Utils.requireNonNull(template, ErrorMessage.ENTITY_NULL);

        final var resourceIds =
                Utils.toStream(template.getResources()).map(x -> build(x).getId())
                        .collect(Collectors.toSet());

        final var catalog = catalogService.create(template.getDesc());

        catalogResourceLinker.replace(catalog.getId(), resourceIds);

        return catalog;
    }

    /**
     * Creates a resource from a resource template.
     *
     * @param template the template.
     * @return the resource.
     */
    public Resource buildResource(final ResourceTemplate template) {
        if (resourceService instanceof RemoteResolver) {
            final var resourceId = ((RemoteResolver) resourceService)
                    .identifyByRemoteId(template.getDesc().getRemoteId());
            if (resourceId.isPresent()) {
                return resourceService.update(resourceId.get(), template.getDesc());
            }
        }

        return resourceService.create(template.getDesc());
    }

    /**
     * Build a resource and dependencies from a template.
     *
     * @param template The resource template.
     * @return The new resource.
     * @throws IllegalArgumentException if the passed template is null.
     */
    public Resource build(final ResourceTemplate template) {
        Utils.requireNonNull(template, ErrorMessage.ENTITY_NULL);

        final var representationIds =
                Utils.toStream(template.getRepresentations()).map(x -> build(x).getId())
                        .collect(Collectors.toSet());
        final var contractIds = Utils.toStream(template.getContracts()).map(x -> build(x).getId())
                .collect(Collectors.toSet());
        final var resource = buildResource(template);

        resourceRepresentationLinker.add(resource.getId(), representationIds);
        resourceContractLinker.add(resource.getId(), contractIds);

        return resource;
    }

    /**
     * Build a representation and dependencies from template.
     *
     * @param template The representation template.
     * @return The new representation.
     * @throws IllegalArgumentException if the passed template is null.
     */
    public Representation build(final RepresentationTemplate template) {
        Utils.requireNonNull(template, ErrorMessage.ENTITY_NULL);

        final var artifactIds = Utils.toStream(template.getArtifacts()).map(x -> build(x).getId())
                .collect(Collectors.toSet());
        Representation representation;
        final var repId =
                representationService.identifyByRemoteId(template.getDesc().getRemoteId());
        if (repId.isPresent()) {
            representation = representationService.update(repId.get(), template.getDesc());
        } else {
            representation = representationService.create(template.getDesc());
        }

        representationArtifactLinker.add(representation.getId(), artifactIds);

        return representation;
    }

    /**
     * Build a contract and dependencies from a template.
     *
     * @param template The contract template.
     * @return The new contract.
     * @throws IllegalArgumentException if the passed template is null.
     */
    public Contract build(final ContractTemplate template) {
        Utils.requireNonNull(template, ErrorMessage.ENTITY_NULL);

        final var ruleIds = Utils.toStream(template.getRules()).map(x -> build(x).getId())
                .collect(Collectors.toSet());
        final var contract = contractService.create(template.getDesc());
        contractRuleLinker.add(contract.getId(), ruleIds);

        return contract;
    }

    /**
     * Build an artifact and dependencies from a template.
     *
     * @param template The artifact template.
     * @return The new artifact.
     * @throws IllegalArgumentException if the passed template is null.
     */
    public Artifact build(final ArtifactTemplate template) {
        Utils.requireNonNull(template, ErrorMessage.ENTITY_NULL);

        Artifact artifact;
        final var contractId = artifactService.identifyByRemoteId(template.getDesc().getRemoteId());
        if (contractId.isPresent()) {
            artifact = artifactService.update(contractId.get(), template.getDesc());
        } else {
            artifact = artifactService.create(template.getDesc());
        }

        return artifact;
    }

    /**
     * Build a rule and dependencies from a template.
     *
     * @param template The rule template.
     * @return The new rule.
     * @throws IllegalArgumentException if the passed template is null.
     */
    public ContractRule build(final RuleTemplate template) {
        Utils.requireNonNull(template, ErrorMessage.ENTITY_NULL);
        return ruleService.create(template.getDesc());
    }
}

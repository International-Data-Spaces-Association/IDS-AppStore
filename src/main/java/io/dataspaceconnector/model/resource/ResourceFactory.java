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
package io.dataspaceconnector.model.resource;

import io.dataspaceconnector.common.exception.InvalidEntityException;
import io.dataspaceconnector.model.named.AbstractNamedFactory;
import io.dataspaceconnector.model.util.FactoryUtils;
import io.dataspaceconnector.service.resource.type.ResourceService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Base class for creating and updating resources.
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class ResourceFactory extends AbstractNamedFactory<Resource, ResourceDesc> {


    /**
     * The service for resource handling.
     */
    private final @NonNull ResourceService resourceService;

    /**
     * The default remote id assigned to all resources.
     */
    public static final URI DEFAULT_REMOTE_ID = URI.create("genesis");

    /**
     * The default keywords assigned to all resources.
     */
    public static final List<String> DEFAULT_KEYWORDS = List.of("DSC");

    /**
     * The default publisher assigned to all resources.
     */
    public static final URI DEFAULT_PUBLISHER = URI.create("");

    /**
     * The default language assigned to all resources.
     */
    public static final String DEFAULT_LANGUAGE = "";

    /**
     * The default license assigned to all resources.
     */
    public static final URI DEFAULT_LICENSE = URI.create("");

    /**
     * The default sovereign assigned to all resources.
     */
    public static final URI DEFAULT_SOVEREIGN = URI.create("");

    /**
     * The default endpoint documentation assigned to all resources.
     */
    public static final URI DEFAULT_ENDPOINT_DOCS = URI.create("");

    /**
     * The default payment modality assigned to all resources.
     */
    public static final PaymentMethod DEFAULT_PAYMENT_MODALITY = PaymentMethod.UNDEFINED;

    /**
     * The default sample list assigned to all resources.
     */
    public static final List<URI> DEFAULT_SAMPLES = new ArrayList<>();

    /**
     * Create a new resource.
     *
     * @param desc The description of the new resource.
     * @return The new resource.
     * @throws IllegalArgumentException if desc is null.
     */
    @Override
    protected Resource initializeEntity(final ResourceDesc desc) {
        final var resource = new Resource();
        resource.setRepresentations(new ArrayList<>());
        resource.setContracts(new ArrayList<>());
        resource.setCatalogs(new ArrayList<>());
        resource.setSubscriptions(new ArrayList<>());

        return resource;
    }

    /**
     * Update a resource.
     *
     * @param resource The resource to be updated.
     * @param desc     The new resource description.
     * @return True if the resource has been modified.
     * @throws IllegalArgumentException if any of the parameters is null.
     */
    @Override
    protected boolean updateInternal(final Resource resource, final ResourceDesc desc) {
        final var hasUpdatedRemoteId = updateRemoteId(resource, desc.getRemoteId());
        final var hasUpdatedKeywords = updateKeywords(resource, desc.getKeywords());
        final var hasUpdatedPublisher = updatePublisher(resource, desc.getPublisher());
        final var hasUpdatedLanguage = updateLanguage(resource, desc.getLanguage());
        final var hasUpdatedLicense = updateLicense(resource, desc.getLicense());
        final var hasUpdatedSovereign = updateSovereign(resource, desc.getSovereign());
        final var hasUpdatedEndpointDocs =
                updateEndpointDocs(resource, desc.getEndpointDocumentation());
        final var hasUpdatedPaymentMethod = updatePaymentMethod(resource, desc.getPaymentMethod());
        final var hasUpdatedSamples = updateSamples(resource, desc.getSamples());

        final var hasUpdated = hasUpdatedRemoteId || hasUpdatedKeywords || hasUpdatedPublisher
                || hasUpdatedLanguage || hasUpdatedLicense || hasUpdatedSovereign
                || hasUpdatedEndpointDocs || hasUpdatedPaymentMethod || hasUpdatedSamples;

        if (hasUpdated) {
            resource.setVersion(resource.getVersion() + 1);
        }

        return hasUpdated;
    }

    private boolean updateRemoteId(final Resource resource, final URI remoteId) {
        final var newUri = FactoryUtils.updateUri(
                resource.getRemoteId(), remoteId, DEFAULT_REMOTE_ID);
        newUri.ifPresent(resource::setRemoteId);

        return newUri.isPresent();
    }

    private boolean updateKeywords(final Resource resource, final List<String> keywords) {
        final var newKeys =
                FactoryUtils.updateStringList(resource.getKeywords(), keywords, DEFAULT_KEYWORDS);
        newKeys.ifPresent(resource::setKeywords);

        return newKeys.isPresent();
    }

    private boolean updatePublisher(final Resource resource, final URI publisher) {
        final var newPublisher =
                FactoryUtils.updateUri(resource.getPublisher(), publisher, DEFAULT_PUBLISHER);
        newPublisher.ifPresent(resource::setPublisher);

        return newPublisher.isPresent();
    }

    private boolean updateLanguage(final Resource resource, final String language) {
        final var newLanguage =
                FactoryUtils.updateString(resource.getLanguage(), language, DEFAULT_LANGUAGE);
        newLanguage.ifPresent(resource::setLanguage);

        return newLanguage.isPresent();
    }

    private boolean updateLicense(final Resource resource, final URI license) {
        final var newLicense =
                FactoryUtils.updateUri(resource.getLicense(), license, DEFAULT_LICENSE);
        newLicense.ifPresent(resource::setLicense);

        return newLicense.isPresent();
    }

    private boolean updateSovereign(final Resource resource, final URI sovereign) {
        final var newPublisher =
                FactoryUtils.updateUri(resource.getSovereign(), sovereign, DEFAULT_SOVEREIGN);
        newPublisher.ifPresent(resource::setSovereign);

        return newPublisher.isPresent();
    }

    private boolean updateEndpointDocs(final Resource resource, final URI endpointDocs) {
        final var newPublisher = FactoryUtils.updateUri(
                resource.getEndpointDocumentation(), endpointDocs, DEFAULT_ENDPOINT_DOCS);
        newPublisher.ifPresent(resource::setEndpointDocumentation);

        return newPublisher.isPresent();
    }

    private boolean updatePaymentMethod(final Resource resource,
                                        final PaymentMethod paymentMethod) {
        final var tmp = paymentMethod == null ? DEFAULT_PAYMENT_MODALITY : paymentMethod;
        if (tmp.equals(resource.getPaymentModality())) {
            return false;
        }

        resource.setPaymentModality(tmp);
        return true;
    }

    private boolean updateSamples(final Resource resource, final List<URI> samples) {
        if (samples != null) {
            validateSamples(resource, samples);
        }
        final var newList
                = FactoryUtils.updateUriList(resource.getSamples(), samples, DEFAULT_SAMPLES);
        newList.ifPresent(resource::setSamples);

        return newList.isPresent();
    }

    private void validateSamples(final Resource resource, final List<URI> samples) {
        for (final var sample : samples) {
            if (io.dataspaceconnector.common.util.ValidationUtils.isInvalidUri(sample.toString())) {
                throw new InvalidEntityException("Sample is not a valid uri.");
            }

            UUID resourceId;
            try {
                resourceId = io.dataspaceconnector.common.net.EndpointUtils.getUUIDFromPath(sample);
            } catch (Exception exception) {
                throw new InvalidEntityException("Sample is not a valid uri.");
            }

            if (resource.getId().equals(resourceId)) {
                throw new InvalidEntityException("Resource cannot reference itself.");
            }

            if (!resourceService.doesExist(resourceId)) {
                if (log.isWarnEnabled()) {
                    log.warn("Could not find matching resource. [id=({})]", sample);
                }
                throw new InvalidEntityException("Could not find matching resource.");
            }
        }
    }
}

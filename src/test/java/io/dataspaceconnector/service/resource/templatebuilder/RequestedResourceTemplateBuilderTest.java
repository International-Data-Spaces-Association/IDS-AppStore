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
package io.dataspaceconnector.service.resource.templatebuilder;

import io.dataspaceconnector.model.resource.RequestedResource;
import io.dataspaceconnector.model.resource.RequestedResourceDesc;
import io.dataspaceconnector.model.resource.RequestedResourceFactory;
import io.dataspaceconnector.model.template.ResourceTemplate;
import io.dataspaceconnector.repository.RequestedResourcesRepository;
import io.dataspaceconnector.service.resource.relation.AbstractResourceRepresentationLinker;
import io.dataspaceconnector.service.resource.relation.RequestedResourceContractLinker;
import io.dataspaceconnector.service.resource.type.RequestedResourceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;

class RequestedResourceTemplateBuilderTest {

    private RequestedResourcesRepository repository = Mockito.mock(RequestedResourcesRepository.class);
    @SuppressWarnings("unchecked")
    private AbstractResourceRepresentationLinker<RequestedResource> requestedResourceRepresentationLinker = Mockito.mock(AbstractResourceRepresentationLinker.class);
    private RequestedResourceContractLinker requestedResourceContractLinker = Mockito.mock(RequestedResourceContractLinker.class);

    private RequestedResourceTemplateBuilder builder = new RequestedResourceTemplateBuilder(
            new RequestedResourceService(repository, new RequestedResourceFactory()),
            requestedResourceRepresentationLinker,
            requestedResourceContractLinker,
            Mockito.mock(RepresentationTemplateBuilder.class),
            Mockito.mock(ContractTemplateBuilder.class)
    );

    @BeforeEach
    public void setup() {
        Mockito.doAnswer(returnsFirstArg())
               .when(repository)
               .saveAndFlush(Mockito.any());
    }

    @Test
    public void build_ResourceTemplateNull_throwIllegalArgumentException() {
        /* ACT && ASSERT */
        assertThrows(IllegalArgumentException.class,
                () -> builder.build(null));
    }

    @Test
    public void build_ResourceTemplateOnlyDesc_returnOnlyResource() {
        /* ARRANGE */
        final var desc = new RequestedResourceDesc();
        final var template = new ResourceTemplate<>(desc);

        /* ACT */
        final var result = builder.build(template);

        /* ASSERT */
        assertNotNull(result);
        Mockito.verify(requestedResourceRepresentationLinker, Mockito.atLeastOnce()).add(Mockito.any(), Mockito.any());
        Mockito.verify(requestedResourceContractLinker, Mockito.atLeastOnce()).add(Mockito.any(),
                Mockito.any());
    }
}

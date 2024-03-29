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
package io.dataspaceconnector.controller.resource.type;

import de.fraunhofer.fit.appstore.services.template.ContainerTemplateService;
import io.dataspaceconnector.config.BasePath;
import io.dataspaceconnector.controller.resource.base.BaseResourceNotificationController;
import io.dataspaceconnector.controller.resource.base.tag.ResourceDescription;
import io.dataspaceconnector.controller.resource.base.tag.ResourceName;
import io.dataspaceconnector.controller.resource.view.resource.OfferedResourceView;
import io.dataspaceconnector.controller.util.ResponseCode;
import io.dataspaceconnector.controller.util.ResponseDescription;
import io.dataspaceconnector.model.resource.OfferedResource;
import io.dataspaceconnector.model.resource.OfferedResourceDesc;
import io.dataspaceconnector.service.resource.type.ResourceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.apache.jena.sparql.function.library.leviathan.log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.UUID;
import de.fraunhofer.fit.appstore.exceptions.TemplateException;
import io.dataspaceconnector.common.exception.ResourceNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Offers the endpoints for managing offered resources.
 */
@Log4j2
@RestController
@RequestMapping(BasePath.OFFERS)
@Tag(name = ResourceName.OFFERS, description = ResourceDescription.OFFERS)
public class OfferedResourceController extends BaseResourceNotificationController<OfferedResource,
        OfferedResourceDesc, OfferedResourceView, ResourceService<OfferedResource,
        OfferedResourceDesc>> {

    /**
     * The container template service.
     */
    private final @NonNull ContainerTemplateService containerTemplateService;

    public OfferedResourceController(@NonNull ContainerTemplateService containerTemplateService) {
        this.containerTemplateService = containerTemplateService;
    }

    /**
     * Request container template.
     *
     * @param resId The resource id.
     * @return Response with code 200 (Ok) and a template.
     */
    @Operation(summary = "Request Container Template", description = "Get the app resource's "
            + "container template")
    @ApiResponses(value = {
            @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK),
            @ApiResponse(responseCode = ResponseCode.NOT_FOUND,
                    description = ResponseDescription.NOT_FOUND),
            @ApiResponse(responseCode = ResponseCode.INTERNAL_SERVER_ERROR,
                    description = ResponseDescription.INTERNAL_SERVER_ERROR)
    })
    @RequestMapping(value = "/{id}/template", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> getContainerTemplate(
            @Parameter(description = "The resource uuid.", required = true)
            @PathVariable("id") final UUID resId) {

        try {
            final var template = containerTemplateService.createContainerTemplate(resId);
            return new ResponseEntity<>(template, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            if (log.isDebugEnabled()) {
                log.debug("Could not find app resource. [exception=({})]", e.getMessage());
            }
            return new ResponseEntity<>("Could not find app resource.", HttpStatus.NOT_FOUND);
        } catch (TemplateException e) {
            if (log.isDebugEnabled()) {
                log.debug("Could not create container template. [exception=({})]", e.getMessage());
            }
            return new ResponseEntity<>("Could not create container template.",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

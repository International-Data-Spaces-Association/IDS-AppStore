/*
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
package de.fraunhofer.fit.appstore.controller.registry.webhook;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.fraunhofer.fit.appstore.exceptions.WebhookException;
import de.fraunhofer.fit.appstore.model.registry.webhook.Event;
import de.fraunhofer.fit.appstore.services.registry.webhook.RegistryWebhookService;
import io.dataspaceconnector.common.exception.InvalidResourceException;
import io.dataspaceconnector.common.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Provides a webhook endpoint for the registry.
 */
@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/webhook")
@Tag(name = "Backend: Registry Webhooks", description = "Endpoints for Registry Webhooks")
public class RegistryWebhookController {

    /**
     * Service for handling registry webhooks.
     */
    private final @NonNull RegistryWebhookService registryWebhookService;

    /**
     * The object mapper.
     */
    private final @NonNull ObjectMapper objectMapper;

    /**
     * Provides a rest endpoint for registry webhooks.
     *
     * @param webhook The webhook.
     * @return Response with code 200 (Ok).
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @RequestMapping(value = "/registry", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> registryWebhook(
            @Parameter(description = "The JSON Webhook event", required = true)
            @RequestBody final String webhook) {
        try {
            final var event = objectMapper.readValue(webhook, Event.class);
            registryWebhookService.processRegistryEvent(event);
            return new ResponseEntity<>("Successfully processed event.", HttpStatus.OK);
        } catch (WebhookException e) {
            if (log.isDebugEnabled()) {
                log.debug("Failed to process webhook event. [exception=({})]", e.getMessage());
            }
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ResourceNotFoundException e) {
            if (log.isDebugEnabled()) {
                log.debug("Resource not found. [exception=({})]", e.getMessage());
            }
            return new ResponseEntity<>("Failed to update the app. The app resource or "
                    + "representation does not exist.", HttpStatus.NOT_FOUND);
        } catch (InvalidResourceException e) {
            if (log.isDebugEnabled()) {
                log.debug("Invalid representation or resource. [exception=({})]",
                        e.getMessage());
            }
            return new ResponseEntity<>("Failed to update the app. The app resource or "
                    + "representation is not valid.", HttpStatus.BAD_REQUEST);
        } catch (JsonMappingException e) {
            if (log.isDebugEnabled()) {
                log.debug("Could not map JSON webhook. [exception=({})]", e.getMessage());
            }
            return new ResponseEntity<>("Could not map JSON webhook.",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (JsonProcessingException e) {
            if (log.isWarnEnabled()) {
                log.warn("Could not process JSON webhook. [exception=({})]", e.getMessage());
            }
            return new ResponseEntity<>("Could not process JSON webhook.",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

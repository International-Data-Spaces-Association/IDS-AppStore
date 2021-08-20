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
package de.fraunhofer.fit.appstore.controller.registry;

import de.fraunhofer.fit.appstore.exceptions.RegistryException;
import de.fraunhofer.fit.appstore.services.registry.RegistryService;
import de.fraunhofer.fit.harbor.client.invoker.ApiException;
import de.fraunhofer.fit.harbor.client.model.UserCreationReq;
import de.fraunhofer.fit.harbor.client.model.UserEntity;
import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Provides rest endpoints for registry management.
 */
@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/registry")
@Tag(name = "Registry: User and Container management",
        description = "Endpoints for backendApp handling")
public class RegistryClientController {

    /**
     * Service for registry handling.
     */
    private final @NonNull RegistryService registryService;

    /**
     * Provides a rest endpoint for retrieving registry users.
     *
     * @return Response with code 200 (Ok) and a list of all users.
     */
    @Operation(summary = "Get user list", description = "Gets the registry user list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> getRegistryUsers() {
        try {
            final var users = registryService.listUsers();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (ApiException exception) {
            if (log.isWarnEnabled()) {
                log.warn("Failed to get users. [exception=({})]", exception.getMessage());
            }
            return new ResponseEntity<>("Failed to get users.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Provides a rest endpoint for creating users.
     *
     * @param registryUser The new user.
     * @return Response with code 201 (Created).
     */
    @Operation(summary = "Create new user", description = "Creates a new user in the registry. "
            + "Password or secret must be longer than 8 chars with at least 1 uppercase letter, 1 "
            + "lowercase letter and 1 number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> createUser(
            @Parameter(description = "The user") @RequestBody final UserCreationReq registryUser) {
        try {
            registryService.createUser(registryUser.getUsername(), registryUser.getPassword());
            return new ResponseEntity<>("Registry user created.", HttpStatus.CREATED);
        } catch (RegistryException exception) {
            if (log.isDebugEnabled()) {
                log.debug("User already exists. [exception=({})]", exception.getMessage());
            }
            return new ResponseEntity<>("User already exists.", HttpStatus.BAD_REQUEST);
        } catch (ApiException exception) {
            if (log.isWarnEnabled()) {
                log.warn("Failed to create user. [exception=({})]", exception.getMessage());
            }
            return new ResponseEntity<>("Failed to create user.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Provides a rest endpoint for creating a project member.
     *
     * @param projectUserRole The project user role.
     * @param projectUser     The project user.
     * @return Response with code 201 (Created).
     */
    @Operation(summary = "Create project member")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @RequestMapping(value = "/project/member", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> createProjectMember(
            @Parameter(description = "The project members role. The role id 1 for projectAdmin, 2 "
                    + "for developer, 3 for guest, 4 for maintainer.")
            @RequestParam final Integer projectUserRole,
            @Parameter(description = "The project member", required = true)
            @RequestBody final UserEntity projectUser) {
        try {
            registryService.createProjectMember(projectUser.getUsername(), projectUserRole);
            return new ResponseEntity<>("Project member created.", HttpStatus.CREATED);
        } catch (IllegalArgumentException exception) {
            if (log.isDebugEnabled()) {
                log.debug("Invalid input. [exception=({})]", exception.getMessage());
            }
            return new ResponseEntity<>("Invalid input.", HttpStatus.BAD_REQUEST);
        } catch (RegistryException exception) {
            if (log.isDebugEnabled()) {
                log.debug("User not found. [exception=({})]", exception.getMessage());
            }
            return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
        } catch (ApiException exception) {
            if (log.isWarnEnabled()) {
                log.warn("Failed to create member. [exception=({})]", exception.getMessage());
            }
            return new ResponseEntity<>("Failed to create member.",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Provides a rest endpoint for creating user credentials.
     *
     * @return Response with code 201 (Created) and the user credentials.
     */
    @Operation(summary = "Create user credentials for pulling images")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> createUserCredentialsForPullingImages() {
        try {
            final var user = registryService.createUserCredentialsForPullingImages();
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (IllegalArgumentException exception) {
            if (log.isDebugEnabled()) {
                log.debug("Invalid input. [exception=({})]", exception.getMessage());
            }
            return new ResponseEntity<>("Invalid input.", HttpStatus.BAD_REQUEST);
        } catch (RegistryException exception) {
            if (log.isDebugEnabled()) {
                log.debug("User not found. [exception=({})]", exception.getMessage());
            }
            return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
        } catch (ApiException exception) {
            if (log.isWarnEnabled()) {
                log.warn("Failed to create credentials. [exception=({})]", exception.getMessage());
            }
            return new ResponseEntity<>("Failed to create credentials.",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

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
package de.fraunhofer.fit.appstore.services.registry;

import de.fraunhofer.fit.appstore.config.RegistryAPIClientConfig;
import de.fraunhofer.fit.appstore.exceptions.RegistryException;
import de.fraunhofer.fit.harbor.client.api.MemberApi;
import de.fraunhofer.fit.harbor.client.api.PingApi;
import de.fraunhofer.fit.harbor.client.api.UserApi;
import de.fraunhofer.fit.harbor.client.invoker.ApiException;
import de.fraunhofer.fit.harbor.client.model.ProjectMember;
import de.fraunhofer.fit.harbor.client.model.UserCreationReq;
import de.fraunhofer.fit.harbor.client.model.UserEntity;
import de.fraunhofer.fit.harbor.client.model.UserResp;
import de.fraunhofer.fit.harbor.client.model.UserSearchRespItem;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * The registry service.
 */
@Log4j2
@RequiredArgsConstructor
@Service
public class RegistryService {

    /**
     * The user api.
     */
    private final @NonNull UserApi userApi;

    /**
     * The ping api.
     */
    private final @NonNull PingApi pingApi;

    /**
     * The member api.
     */
    private final @NonNull MemberApi memberApi;

    /**
     * Service for registry configurations.
     */
    private final @NonNull RegistryAPIClientConfig registryConfig;

    /**
     * Runs a ping test against an api.
     *
     * @return True if the test was successful.
     */
    private boolean pingApi() {
        final var xRequestedId = "";
        try {
            final var result = this.pingApi.getPing(xRequestedId);
            return result.equals("pong");
        } catch (ApiException e) {
            if (log.isWarnEnabled()) {
                log.warn("Error sending request to registry");
            }
        }
        return false;
    }

    /**
     * List users.
     *
     * @return A list of users.
     * @throws ApiException if the list of users cannot be retrieved.
     */
    public List<UserResp> listUsers() throws ApiException {
        final var xRequestedId = UUID.randomUUID().toString();
        final var query = "";
        final var sort = "";
        final long page = 1;
        final long pageSize = 10;
        return this.userApi.listUsers(xRequestedId, query, sort, page, pageSize);
    }

    /**
     * Search user by name.
     *
     * @param username The username.
     * @return A list of matching users.
     * @throws ApiException if the search could not be processed.
     */
    public List<UserSearchRespItem> searchUserByName(final String username) throws ApiException {
        final var xRequestedId = UUID.randomUUID().toString();
        final long page = 1;
        final long pageSize = 10;
        return this.userApi.searchUsers(username, xRequestedId, page, pageSize);
    }

    /**
     * Check if user exists.
     *
     * @param username The username.
     * @return True if the user exists, false if not.
     * @throws ApiException if the check could not be processed.
     */
    private boolean checkIfUserExists(final String username) throws ApiException {
        final var users = this.searchUserByName(username);

        return users.size() == 1;
    }

    /**
     * Creates a user in the registry. Password or secret must be longer than 8 chars with at least
     * 1 uppercase letter, 1 lowercase letter, and 1 number.
     *
     * @param username The username.
     * @param password The password.
     * @return The user creation request.
     * @throws ApiException      if the user could not be created.
     * @throws RegistryException if the registry could not process the request.
     */
    public UserCreationReq createUser(final String username, final String password)
            throws ApiException, RegistryException {

        if (checkIfUserExists(username)) {
            throw new RegistryException("User already exists!");
        }

        final var xRequestedId = UUID.randomUUID().toString();
        final var userCreationReq = new UserCreationReq();
        userCreationReq.setComment("IDS APPSTORE USER: " + username);
        userCreationReq.setUsername(username);
        userCreationReq.setPassword(password);
        userCreationReq.setRealname(username);
        userCreationReq.setEmail(username + ".appstore@fit.fraunhofer.de");

        this.userApi.createUser(userCreationReq, xRequestedId);
        return userCreationReq;
    }

    /**
     * Create project member.
     *
     * @param username The username.
     * @param roleId   The role id.
     * @throws IllegalArgumentException if the username is invalid.
     * @throws RegistryException        if the registry could not process the request.
     * @throws ApiException             if the member could not be created.
     */
    public void createProjectMember(final String username, final Integer roleId)
            throws IllegalArgumentException, RegistryException, ApiException {

        if (username == null || username.isEmpty() || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be null or empty.");
        }

        if (roleId == null) {
            throw new IllegalArgumentException("RoleId cannot be null.");
        }

        final var projectName = registryConfig.getApiProject();
        if (projectName == null || projectName.isEmpty() || projectName.isBlank()) {
            throw new IllegalArgumentException("ProjectName is not set in properties.");
        }

        // Proof if user already exists and get UserId.
        Integer userId;
        String newUsername;
        if (checkIfUserExists(username)) {
            final var users = this.searchUserByName(username);
            newUsername = users.get(0).getUsername();
            userId = users.get(0).getUserId();
        } else {
            throw new RegistryException("User does not exist in registry.");
        }

        final var userEntity = new UserEntity();
        userEntity.setUsername(newUsername);
        userEntity.setUserId(userId);

        final var member = new ProjectMember();
        member.setMemberUser(userEntity);
        // The role id 1 for projectAdmin, 2 for developer, 3 for guest, 4 for maintainer.
        member.setRoleId(roleId);
        // For ldap groups.
        member.setMemberGroup(null);

        final var xRequestedId = UUID.randomUUID().toString();
        // set true if projectName is String; false if projectName is id
        final boolean xIsResourceName = true;
        this.memberApi.createProjectMember(projectName, xRequestedId, xIsResourceName, member);
    }

    /**
     * Create user credentials for pulling images.
     *
     * @return The user credentials.
     * @throws IllegalArgumentException if the input is invalid.
     * @throws RegistryException        if the registry could not process the request.
     * @throws ApiException             if the credentials could not be created.
     */
    public UserCreationReq createUserCredentialsForPullingImages()
            throws IllegalArgumentException, RegistryException, ApiException {
        final var username = RegistryUtils.createLowercaseUsername();
        final var password = RegistryUtils.createValidPassword();
        final var roleId = 3;

        final var user = this.createUser(username, password);

        this.createProjectMember(username, roleId);

        return user;
    }

    /**
     * Create user credentials for pushing images.
     *
     * @return The user credentials.
     * @throws IllegalArgumentException if the input is invalid.
     * @throws RegistryException        if the registry could not process the request.
     * @throws ApiException             if the credentials could not be created.
     */
    public UserCreationReq createUserCredentialsForPushingImages()
            throws IllegalArgumentException, RegistryException, ApiException {
        final var username = RegistryUtils.createLowercaseUsername();
        final var password = RegistryUtils.createValidPassword();
        final var roleId = 2;

        final var user = this.createUser(username, password);

        this.createProjectMember(username, roleId);

        return user;
    }
}

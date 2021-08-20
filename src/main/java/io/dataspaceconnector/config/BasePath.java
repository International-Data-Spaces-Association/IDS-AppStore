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
package io.dataspaceconnector.config;

/**
 * The list of the api's paths.
 */
public final class BasePath {

    private BasePath() {
        // do nothing
    }

    /**
     * The resource endpoint's base path.
     */
    public static final String RESOURCES = "/api/" + BaseType.RESOURCES;

    /**
     * The representation endpoint's base path.
     */
    public static final String REPRESENTATIONS = "/api/" + BaseType.REPRESENTATIONS;

    /**
     * The contract endpoint's base path.
     */
    public static final String CONTRACTS = "/api/" + BaseType.CONTRACTS;

    /**
     * The artifact endpoint's base path.
     */
    public static final String ARTIFACTS = "/api/" + BaseType.ARTIFACTS;

    /**
     * The rule endpoint's base path.
     */
    public static final String RULES = "/api/" + BaseType.RULES;

    /**
     * The catalog endpoint's base path.
     */
    public static final String CATALOGS = "/api/" + BaseType.CATALOGS;

    /**
     * The contract agreement's base path.
     */
    public static final String AGREEMENTS = "/api/" + BaseType.AGREEMENTS;

    /**
     * The subscriptions' base path.
     */
    public static final String SUBSCRIPTIONS = "/api/" + BaseType.SUBSCRIPTIONS;

    /**
     * The brokers' base path.
     */
    public static final String BROKERS = "/api/" + BaseType.BROKERS;

    /**
     * The configurations' base path.
     */
    public static final String CONFIGURATIONS = "/api/" + BaseType.CONFIGURATIONS;

    /**
     * The endpoints' base path.
     */
    public static final String ENDPOINTS = "/api/" + BaseType.ENDPOINTS;

    /**
     * The apps' base path.
     */
    public static final String APPS = "/api/" + BaseType.APPS;
}

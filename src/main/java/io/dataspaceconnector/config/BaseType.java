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
public final class BaseType {

    private BaseType() {
        // do nothing
    }

    /**
     * The resource endpoint's base path.
     */
    public static final String RESOURCES = "resources";

    /**
     * The representation endpoint's base path.
     */
    public static final String REPRESENTATIONS = "representations";

    /**
     * The contract endpoint's base path.
     */
    public static final String CONTRACTS = "contracts";

    /**
     * The artifact endpoint's base path.
     */
    public static final String ARTIFACTS = "artifacts";

    /**
     * The data app endpoint's base path.
     */
    public static final String APPS = "apps";

    /**
     * The rule endpoint's base path.
     */
    public static final String RULES = "rules";

    /**
     * The catalog endpoint's base path.
     */
    public static final String CATALOGS = "catalogs";

    /**
     * The contract agreement's base path.
     */
    public static final String AGREEMENTS = "agreements";

    /**
     * The subscriptions' base path.
     */
    public static final String SUBSCRIPTIONS = "subscriptions";

    /**
     * The brokers' base path.
     */
    public static final String BROKERS = "brokers";

    /**
     * The configurations' base path.
     */
    public static final String CONFIGURATIONS = "configurations";

    /**
     * The endpoints' base path.
     */
    public static final String ENDPOINTS = "endpoints";
}

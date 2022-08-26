package io.dataspaceconnector.model.endpoint;

import com.fasterxml.jackson.annotation.JsonProperty;
//
///**
// * The endpoint types.
// */
//public enum AppEndpointType {
//
//    /**
//     * Endpoint type is APP.
//     */
//    APP("App"),
//
//    /**
//     * Endpoint type is CONNECTOR.
//     */
//    CONNECTOR("Connector"),
//
//    /**
//     * Endpoint type is GENERIC.
//     */
//    GENERIC("Generic");
//
//    /**
//     * Holds the enums string.
//     */
//    private final String value;
//
//    /**
//     * Constructor.
//     *
//     * @param name The name of the endpoint type.
//     */
//    EndpointType(final String name) {
//        this.value = name;
//    }
//
//    /**
//     * The endpoint types.
//     */
    public enum AppEndpointType {

        /**
         * Configuration endpoint.
         */
        @JsonProperty("Configuration")
        CONFIG_ENDPOINT,

        /**
         * Input endpoint.
         */
        @JsonProperty("Input")
        INPUT_ENDPOINT,

        /**
         * Output endpoint.
         */
        @JsonProperty("Output")
        OUTPUT_ENDPOINT,

        /**
         * Status endpoint.
         */
        @JsonProperty("Status")
        STATUS_ENDPOINT,

        /**
         * Usage policy endpoint.
         */
        @JsonProperty("UsagePolicy")
        USAGE_POLICY_ENDPOINT
    }

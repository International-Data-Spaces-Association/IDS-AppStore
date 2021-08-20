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
package de.fraunhofer.fit.appstore.model.registry.webhook;

import java.io.Serializable;

/**
 * The event type.
 */
public enum EventType implements Serializable {

    /**
     * Event that indicates that an artifact was pushed.
     */
    PUSH_ARTIFACT,

    /**
     * Event that indicates that an artifact was pulled.
     */
    PULL_ARTIFACT,

    /**
     * Event that indicates that an artifact was deleted.
     */
    DELETE_ARTIFACT,

    /**
     * Event that indicates that a chart was uploaded.
     */
    UPLOAD_CHART,

    /**
     * Event that indicates that a chart was downloaded.
     */
    DOWNLOAD_CHART,

    /**
     * Event that indicates that a chart was deleted.
     */
    DELETE_CHART,

    /**
     * Event that indicates that a scan is completed.
     */
    SCANNING_COMPLETED,

    /**
     * Event that indicates that a scan failed.
     */
    SCANNING_FAILED,

    /**
     * Event that indicates that a quota exceeded.
     */
    QUOTA_EXCEED,

    /**
     * Event that indicates a quota warning.
     */
    QUOTA_WARNING,

    /**
     * Event that indicates a replication.
     */
    REPLICATION
}

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

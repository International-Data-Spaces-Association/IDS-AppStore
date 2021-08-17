package de.fraunhofer.fit.appstore.model.registry.webhook;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * The repository.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Repository implements Serializable {

    /**
     * Default serial version uid.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The creation date.
     */
    @JsonProperty("date_created")
    private long dateCreated;

    /**
     * The name.
     */
    @JsonProperty("name")
    private String name;

    /**
     * The namespace.
     */
    @JsonProperty("namespace")
    private String namespace;

    /**
     * The full name.
     */
    @JsonProperty("repo_full_name")
    private String repoFullName;

    /**
     * The type.
     */
    @JsonProperty("repo_type")
    private String repoType;

}

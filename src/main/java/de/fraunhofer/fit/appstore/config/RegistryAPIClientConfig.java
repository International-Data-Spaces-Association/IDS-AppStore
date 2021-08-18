package de.fraunhofer.fit.appstore.config;

import de.fraunhofer.fit.harbor.client.api.MemberApi;
import de.fraunhofer.fit.harbor.client.api.PingApi;
import de.fraunhofer.fit.harbor.client.api.ProjectApi;
import de.fraunhofer.fit.harbor.client.api.UserApi;
import de.fraunhofer.fit.harbor.client.invoker.ApiClient;
import de.fraunhofer.fit.harbor.client.invoker.auth.HttpBasicAuth;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static de.fraunhofer.fit.harbor.client.invoker.Configuration.getDefaultApiClient;

/**
 * Configurations for the registry api client.
 */
@Configuration
@Data
public class RegistryAPIClientConfig {

    /**
     * The registry api url.
     */
    @Value("${registry.api.url}")
    private String apiUrl;

    /**
     * The registry url.
     */
    @Value("${registry.url}")
    private String registryUrl;

    /**
     * The registry host.
     */
    @Value("${registry.host}")
    private String registryHost;

    /**
     * The registry api username.
     */
    @Value("${registry.client.user}")
    private String apiUsername;

    /**
     * The registry api password.
     */
    @Value("${registry.client.password}")
    private String apiPassword;

    /**
     * The registry client project.
     */
    @Value("${registry.project}")
    private String apiProject;

    /**
     * Getter for user api.
     *
     * @return The user api.
     */
    @Bean
    public UserApi userApi() {
        return new UserApi(apiClient());
    }

    /**
     * Getter for ping api.
     *
     * @return The ping api.
     */
    @Bean
    public PingApi pingApi() {
        return new PingApi(apiClient());
    }

    /**
     * Getter for project api.
     *
     * @return The project api.
     */
    @Bean
    public ProjectApi projectApi() {
        return new ProjectApi(apiClient());
    }

    /**
     * Getter for member api.
     *
     * @return The member api.
     */
    @Bean
    public MemberApi memberApi() {
        return new MemberApi(apiClient());
    }

    /**
     * Getter for api client.
     *
     * @return The api client.
     */
    @Bean
    public ApiClient apiClient() {
        final var client = getDefaultApiClient();
        client.setBasePath(this.apiUrl);

        // Configure HTTP basic authorization: basic
        final var basic = (HttpBasicAuth) client.getAuthentication("basic");
        basic.setUsername(this.apiUsername);
        basic.setPassword(this.apiPassword);

        return client;
    }

}

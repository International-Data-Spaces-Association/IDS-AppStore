package io.dataspaceconnector.service.resource.ids.builder;

import de.fraunhofer.iais.eis.SmartDataApp;
import de.fraunhofer.iais.eis.SmartDataAppBuilder;
import io.dataspaceconnector.common.net.SelfLinkHelper;
import de.fraunhofer.iais.eis.util.ConstraintViolationException;
import io.dataspaceconnector.common.ids.mapping.ToIdsObjectMapper;
import io.dataspaceconnector.model.app.App;
import io.dataspaceconnector.model.endpoint.AppEndpoint;
import io.dataspaceconnector.service.resource.ids.builder.base.AbstractIdsBuilder;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Converts dsc apps to ids data apps.
 */
@Component
public final class IdsDataAppBuilder extends AbstractIdsBuilder<App, SmartDataApp> {

    /**
     * The builder for ids dataApp.
     */
    private final @NonNull IdsAppEndpointBuilder endpointBuilder;

    public IdsDataAppBuilder(@NonNull SelfLinkHelper selfLinkHelper, @NonNull IdsAppEndpointBuilder endpointBuilder) {
        super(selfLinkHelper);
        this.endpointBuilder = endpointBuilder;
    }


    @Override
    protected de.fraunhofer.iais.eis.SmartDataApp createInternal(final App app,
                                                                 final int currentDepth,
                                                                 final int maxDepth)
            throws ConstraintViolationException {

        // Build children.
        final var endpoints = create(endpointBuilder, app.getEndpoints(), currentDepth, maxDepth);

        if (endpoints.isEmpty() || endpoints.get().isEmpty()) {
            return null;
        }

        // Prepare SmartDataApp attributes.
        final var selfLink = getAbsoluteSelfLink(app);
        final var documentation = app.getDocs();
        final var environmentVariables = app.getEnvVariables();
        final var storageConfiguration = app.getStorageConfig();

        final var usagePolicies
                = ToIdsObjectMapper.getUsagePolicyClass(app.getSupportedPolicies().get(0));

//                Optional<List<AppEndpoint>> appEndpoints
//                = Optional.of(List.<AppEndpoint>copyOf((Collection<? extends AppEndpoint>) endpoints.get()));

        final var builder = new SmartDataAppBuilder(selfLink)
                ._appDocumentation_(documentation)
                ._appEnvironmentVariables_(environmentVariables)
                ._appStorageConfiguration_(storageConfiguration)
                ._supportedUsagePolicies_(usagePolicies);
//                ._appEndpoint_(appEndpoints);

        endpoints.ifPresent(x -> builder._appEndpoint_((de.fraunhofer.iais.eis.AppEndpoint) Collections.unmodifiableList(x).get(0)));

        return builder.build();
    }
}

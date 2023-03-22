package io.dataspaceconnector.service.resource.relation;

import io.dataspaceconnector.model.app.App;
import io.dataspaceconnector.model.artifact.Artifact;
import io.dataspaceconnector.model.representation.Representation;
import io.dataspaceconnector.service.resource.base.OwningRelationService;
import io.dataspaceconnector.service.resource.type.AppService;
import io.dataspaceconnector.service.resource.type.ArtifactService;
import io.dataspaceconnector.service.resource.type.RepresentationService;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Handles the relation between representations and apps.
 */
@Service
@NoArgsConstructor
public class RepresentationAppLinker extends OwningRelationService<Representation, App,
        RepresentationService, AppService> {

    /**
     * Get the list of apps owned by a given representations.
     *
     * @param owner The owner of the apps.
     * @return The list of owned apps.
     */
    @Override
    protected List<App> getInternal(final Representation owner) {
        return owner.getDataApps();
    }
}

package io.dataspaceconnector.controller.resource.relation;

import io.dataspaceconnector.config.BasePath;
import io.dataspaceconnector.config.BaseType;
import io.dataspaceconnector.controller.resource.base.BaseResourceChildController;
import io.dataspaceconnector.controller.resource.base.tag.ResourceDescription;
import io.dataspaceconnector.controller.resource.base.tag.ResourceName;
import io.dataspaceconnector.controller.resource.view.endpoint.AppEndpointView;
import io.dataspaceconnector.controller.resource.view.representation.RepresentationView;
import io.dataspaceconnector.model.endpoint.AppEndpoint;
import io.dataspaceconnector.model.representation.Representation;
import io.dataspaceconnector.service.resource.relation.AppEndpointLinker;
import io.dataspaceconnector.service.resource.relation.AppRepresentationLinker;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * Offers the endpoints for managing the relations between apps and representations.
 */
@RestController
@RequestMapping(BasePath.APPS + "/{id}/" + BaseType.REPRESENTATIONS)
@Tag(name = ResourceName.APPS, description = ResourceDescription.APPS)
public class AppsToRepresentationsController extends BaseResourceChildController<
        AppRepresentationLinker, Representation, RepresentationView> {
}

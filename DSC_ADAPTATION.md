# List of Changes/Extensions to DSC Code

Changes in DSC code are marked with the comment
`/* AppStore Extension */`

## io.dsc.controller.MainController

needs to be deleted, replace by MainController in de.fraunhofer.fit.appstore

Test io.dataspaceconnector.controller.resource.view.ViewEqualsTest refers to MainController, needs also to be deleted

## io.dsc.common.ids.mapping.RdfConverter

* Added 3 implementations of toRdf method:
  * for AppStore
  * for DataApp
  * for Endpoint
TODO: check whether toRdf(BaseConnector) needs to be deleted

TODO: can we move additional methods to separate class?

## io.dsc.common.ids.mapping.ToIdsObjectMapper

* Added 3 methods
--Ahmad yes, used in different locations
  * getAppEndpointType (currently not used, do we need it? Mapping of EndpointType to AppEndpointType not easy, why do we need it?)
  * getUsagePolicyClass
  * getListOfUsagePolicyClasses

TODO: can we move additional methods to separate class? --> should be possible, but requires change in caller's code

TODO: requires also change of io.dsc.model.endpoint.EndpointType, which side effects?
==============================================

## io.dsc.common.ids.ConnectorService

* Added 2 methods
  * getAppStoreWithAppResources
  * getAppStoreWithoutResources
* Changed method updateConfigModel
  * replace line:
   ```
   final var connector = getConnectorWithOfferedResources();
  ```
    with
  ```
  final var connector = getAppStoreWithAppResources();
  ```

TODO: can we move additional methods to separate class? --> should be possible, but requires change in caller's code

TODO: do we need to delete getConnectorWithOfferedResources and getConnectorWithoutResource?

## io.dsc.common.net.SelfLinkHelper
TODO: probably needs adaptation for App, Endpoint, GenericEndpoint, ConnectorEndpoint, Route


## io.dsc.controller.resource.relation

Added Classes:
* AppsToRepresentationsController
* RepresentationsToAppsController
--Ahmad yes that is needed and also we link them using the python script
TODO: really required?

## io.dsc.controller.resource.view.representation.RepresentationView

Added two members:
* private String runtimeEnvironment
* private URI distributionService


## io.dsc.controller.resource.view.representation.RepresentationViewAssembler

Added at the end of toModel():
```
final var appLink = linkTo(methodOn(RepresentationsToAppsController.class)
    .getResource(representation.getId(), null, null))
    .withRel(BaseType.APPS);
view.add(appLink);
```

## io.dsc.model.resource.Resource
Add annotation `@Indexed` to enable Hibernate Full Text Search

--Ahmad Maybe we need to do the above for the io.dsc.model.resource.offeredResource


## io.dsc.model.app.App

Added property `representations`

Add annotation `@Indexed` to enable Hibernate Full Text Search

Added properties for SecurityScan and Registry

## io.dsc.model.app.AppDesc

similar as before

## io.dsc.model.endpoint
--Ahmed TOCheck
Do we need to add mediaType, port, protocol, type?
If yes, then Endpoint, EndpointDesc, EndpointFactory have to be updated.

## io.dataspaceconnector.model.representation.Representation

Added properties:
* runtimeEnvironment
* distributionService
* dataApps

## io.dataspaceconnector.model.representation.RepresentationDesc

Added properties:
* runtimeEnvironment
* distributionService

## io.dataspaceconnector.model.representation.RepresentationFactory

Added two members:
* DEFAULT_ENVIRONMENT="docker"
* defaultDistributionService

Added to initializeEntity before return:
* `representation.setDataApps(new ArrayList<>());`

Added/modified in updateInternal:
```
final var hasUpdatedRuntimeEnvironment = this.updateRuntimeEnvironment(representation,
    desc.getRuntimeEnvironment());
final var hasUpdatedDistributionService = this.updateDistributionService(representation,
    desc.getDistributionService());

return hasUpdatedRemoteId || hasUpdatedLanguage || hasUpdatedMediaType
    || hasUpdatedStandard || hasUpdatedRuntimeEnvironment
    || hasUpdatedDistributionService;
```

Added methods:
* updateRuntimeEnvironment
* updateDistributionService


## io.dsc.service.message.builder.ConnectorUnavailableMessageBuilder

In method processInternal, replace
```
final var connector = connectorService.getConnectorWithoutResources();
```
with
```
final var connector = connectorService.getAppStoreWithoutResources();
```

## io.dsc.service.message.builder.ConnectorUpdateMessageBuilder

Same as before

## io.dsc.service.message.builder.QueryMessageBuilder

Same as before

TOOD: Do we need to do same somewhere else (in this package)?

## io.dsc.service.message.handler.processor.SelfDescriptionProcessor

Similar as before, but now **with** resources, i.e., replace
```
final var connector = connectorService.getConnectorWithOfferedResources();
```
with
```
final var connector = connectorService.getAppStoreWithAppResources();
```

## io.dsc.service.message.handler.processor.DataRequestProcessor

Added code in processInternal to contact RegistryService

## io.dsc.service.resource.ids.builder.IdsCatalogBuilder

```
final var resources = create(resourceBuilder,
      catalog.getOfferedResources(), currentDepth, maxDepth);
```

```
final var appResources = create(resourceBuilder,
                catalog.getOfferedResources(), currentDepth, maxDepth);

Optional<List<OfferedResource>> resources = Optional.empty();
if (appResources.isPresent()) {
     resources = Optional.of(List.copyOf(appResources.get()));
}
```

## io.dsc.service.resource.relation.RepresentationAppLinker

Added class

# TODO
## io.dsc.service.resource.ids.builder.IdsEndpointBuilder

TODO: Probably needs also updates...

## io.dsc.service.resource.ids.builder.IdsRepresentationBuilder

## io.dsc.service.resource.ids.builder.IdsResourceBuilder

## io.dsc.service.resource.type.EndpointService

## io.dsc.service.EntityResolver


## io.dsc.ConnectorApplication

Add `"de.fraunhofer.fit.appstore.*"` to ComponentScan

##Ahmad changes
(1)
comment parts to allow post /api/apps
io.dataspaceconnector.controller.resource.type.AppController

(2)
io.dataspaceconnector.controller.resource.relation.AppsToEndpointsController
Change BaseResourceChildRestrictedController to BaseResourceChildController to allow link the apps with endpoints

(3)
comment parts to allow post /api/apps
io.dataspaceconnector.controller.resource.type.EndpointController

(4)
add some lines to find a solution when download an app and the endpoint language is Null
the lines are titled with the comment "TOTest  a solution for endpoint language =Null"

## io.dataspaceconnector.model.endpoint.EndpointFactory
Added:
public static final String DEFAULT_LANGUAGE = "EN";

and
protected final boolean updateLanguage(final Endpoint  endpoint, final String language) {
final var newLanguage =
FactoryUtils.updateString(endpoint.getLanguage(), language, DEFAULT_LANGUAGE);
newLanguage.ifPresent(endpoint::setLanguage);
        return newLanguage.isPresent();
    }


## io.dataspaceconnector.model.app.AppFactory

Added:
app.setRepresentations(new ArrayList<>());

## io.dataspaceconnector.model.endpoint.Endpoint
Added:
private String language;



(5)
Update the python script to work with the new appstore version
api/resources to api/offers also adding new properties of api/apps
the current python is found under scripts/tests/

TODO:
#de.fraunhofer.fit.appstore.controller.ui.UiController
# to replace component scan the assigned URLs of the VM servers.

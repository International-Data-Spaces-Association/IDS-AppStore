---
layout: default
title: Usage Control
nav_order: 8
description: ""
permalink: /usage-control
---

# IDS Data Apps and Usage Control Integration

The intent of this document is to provide an overview of the various options for Usage Control integration in an IDS Data App. In addition, a summary of Usage Control Policies that are relevant for an implementation of Usage Control in Data Apps will be given.

## Overview about Usage Control Integrations

| Integration                                      | Profile                                          | Enforcement Level           |
| ------------------------------------------------ | ------------------------------------------------ | --------------------------- |
|  [Interceptor](#interceptor)                     | Basic, Advanced                                  | Data Route                  |
|  [Interceptor with PIP](#interceptor-pip)        | Basic, Advanced                                  | Data Route                  |
|  [Usage Control App (PIP /PEP)](#ucapp)          | Basic - Usage Control, Advanced - Usage Control  | UC APP + IDS APP            |
|  [In-App Usage Control Framework](#in-app-ucf)   | Basic - Usage Control, Advanced - Usage Control  | IDS APP (UCF integrated)    |

## <a name="interceptor"> Usage Control with Data Apps via Interceptor (Route Level) </a>

It is possible to enforce certain high-level usage control policies at the data transfer level of a connector. For a better overview of the policies that can be enforced at the route level, a look can be taken at the table in chapter XX. 
The interceptor pattern is used here, which means that communication between the connector and the data apps is affected directly.
The interceptor is integrated directly into the defined data routes and thus has direct access to the exchanged data and metadata.

![Interceptor Pattern Overview](./assets/images/uc_interceptor_overview.png)

### Extend Data Flow with Usage Control Information
In order for Data Apps to be integrated into the Interceptor pattern, additional information for the implementation of policies must be provided in addition to the transmitted data.
The additional information consists of:
- **TargetDataUri:** The URI of the artifact to be transferred.
- **ContractId:** The ID of the contract that belongs to the transferred artifact.
- **AppName:** The name of the app to which the message will be sent
- **AppUri:** The URI of the app to which the message is sent.

#### Integrate Additional Usage Control Information within HTTP Communication

If it is assumed that currently HTTP is the most used communication protocol for communication between data apps, this additional information can be provided using HTTP headers.
The use of header fields can prevent the usage control information from being sent in an extra payload format.
If the data to be transmitted is packed into the payload and the additional information is located in the header fields, normal HTTP requests can be used. The header fields must be named as mentioned in the previous chapter.

### Integrate Additional Usage Control Information with an Policy Information Point (PIP)

If more additional information from a data app is to be used for usage control in addition to the information from the data flow, an implementation of a policy information point in a data app becomes necessary.
Such an endpoint can be declared in an app implementation with the ids:appEndpointType idsc:USAGE_ENDPOINT. 
The following information can then be retrieved via this Policy Information Point:
- **AppName:** The name of the app
- **AppURI:** The URI of the app or another identifier
- **AppPurpose:** The general purpose of an app.
- **AppActions:** The superclass of a policy: Usage, Distribute, Execute.

In this approach, the Data App provides information about itself using a Policy Information Point (ids:appEndpointType idsc:USAGE_ENDPOINT). However, since a data app only provides information at this point and does not provide any usage control implementations itself, all apps that follow this approach can be described under the **Base Profile**.

![Interceptor Pattern PIP](./assets/images/uc_interceptor_pip.png)
 
## <a name="ucapp"> Usage Control Awareness with Data Apps using the Usage Control App (HTTP-REST Integration) </a>

In this approach, several policy enforcement points are implemented in addition to the policy information point already described.
The enforcement points are calls to the usage control app that are issued when a data app calls certain program parts or methods in order to inform the usage control app. 
This means that the Usage Control App can use the hooks to enforce Usage Control Policies on a much more granular level than on the pure data flow between apps or the connector.
For example, if you look at the purpose "Use", it can not only be applied to the pure use of the data itself as before, but can be separated more precisely using the hooks.
This results, for example, in more finely specified actions such as Process, Display or Store.

The IDS Data App must implement the interfaces of the Usage Control App and its data format to implement the hooks.
This specific integration and the active sending of the hooks to the Usage Control App results in a Data App being assigned to the **Advanced Profile**.

![PIP_PEP Integration](./assets/images/uc_pip_pep.png)

## <a name="in-app-ucf"> Usage Control Awareness with Data Apps using an Usage Control Framework (In-App Implementation)</a>

The third approach to integrating usage control into data apps is to implement a standalone usage control framework in the IDS data app itself. The implementation of this approach requires the already known policy enforcement points on the part of an app and on the other side a complete usage control framework is built into the app. The integration of a complete Usage Control Framework with all its components, such as a Policy Decision Point (PDP) or a Policy Management Point (PMP) and their associated interfaces and data formats is assumed at this point. This solution offers the greatest degree of freedom in terms of the technologies used, starting with the selection of the usage control framework, the programming language and the data formats. However, in addition to the additional effort on the part of the developers, the considerable effort required for the additional management of the policies in a stand-alone system should also be considered.

Due to the fact that a complete usage control framework is integrated and the policy management is completely taken over by the app at this point, all apps that follow this integration are automatically assigned to the **Advanced Profile** of a data app.

![Standalone Integration](./assets/images/uc_standalone_app.png)

# Usage Control Policies for Data Apps
 

## <a name="uc-policies"> Overview of Usage Control Policies </a>
The following section will take a closer look at the Usage Control Policies relevant to IDS Data Apps and where they can be enforced.

| Policy                                                                          | Data Route | UC APP | IDS APP| IDS Connector |
| ------------------------------------------------------------------------------- | -----------|--------|--------| --------- |
|  [Allow the usage of data](#ucpo1)                                              | X          | X      | X      |     X     |
|  [Connector-restricted data usage](#ucpo2)                                      | X          | O      | O      |     X     |
|  [Application-restricted data usage](#ucpo3)                                    | X          | X      | O      |     O     |
|  [Interval-restricted data usage](#ucpo4)                                       | O          | X      | O      |     O     |
|  [Duration-restricted data usage](#ucpo5)                                       | O          | X      | O      |     O     |
|  [Location-restricted data usage](#ucpo6)                                       | O          | X      | X      |     X     |
|  [Purpose-restricted data usage](#ucpo7)                                        | O          | X      | O      |     O     |
|  [Event-restricted data usage](#ucpo8)                                          | O          | X      | O      |     O     |
|  [Use the data not more than N times](#ucpo9)                                   | O          | X      | X      |     O     |
|  [Allow use of data and delete it after](#ucpo10)                               | O          | O      | X      |     O     |
|  [Modify data (in transit)](#ucpo11)                                            | O          | X      | O      |     O     |
|  [Log the data usage information](#ucpo12)                                      | O          | X      | O      |     X     |
|  [Notify a party or a specific group of users when the data is used](#ucpo13)   | O          | X      | O      |     X     |
|  [Attach Policy when distribute to a third-party](#ucpo14)                      | O          | O      | X      |     X     |
|  [Distribute only if encrypted](#ucpo15)                                        | X          | X      | X      |     X     |
|  [Restrict the data usage to specific state](#ucpo16)                           | O          | X      | O      |     X     |


## <a name="ucpo1"> 1. Allow the usage of data </a>
This policy describes whether the general use of data is allowed or prohibited. Since the concept of use has so far only existed in very general terms at the level of a few categories, further classification is required in many use cases.

The categories of use defined so far can be implemented on different levels. Some can already be implemented on data routes using the interceptor pattern, whereas others require so-called actions, which on the one hand require an implementation in the IDS Data App and on the other hand a registration with the Usage Control App. 

The actions can be built in a hierarchical structure to support enforcement on a higher levels as well as on a more granular level of individual actions.

The enforcement of policies from this category is based on black- or whitelisting to restrict the data usage.

## <a name="ucpo2"> 2. Connector-restricted data usage (Route restricted data usage) </a>
The data can only be processed by or transfered to an app that belongs to the same data route definition or at least is running on the same connector.

## <a name="ucpo3"> 3. Application-restricted data usage </a>
The data can only be processed by or transfered to a specific app. The usage control app uses an app identifier given by an policy information endpoint to proof an app identity.

## <a name="ucpo4"> 4. Interval-restricted data usage </a>
The data can only be used by Data Apps within a given intervall. The usage control app (UC APP) can be used to proof the valid intervall.

## <a name="ucpo5"> 5. Duration-restricted data usage </a>
The data can only be used by Data Apps within a given duration. The usage control app (UC APP) can be used to proof the valid duration.

## <a name="ucpo6"> 6. Location-restricted data usage </a>
The data can only be used by IDS Data Apps that belong to the same geolocation.
The location is the same as the connector location for all apps without a graphical user interface.
For data apps with a graphical user interface, you have to decide whether to use the connector location or the location of the calling interface, which can be in a different location than the hosting connector.

## <a name="ucpo7"> 7. Purpose-restricted data usage </a>
The Ids Data Apps can communicate their purpose via a policy information point. Based on the purppose, the Usage Control App decides whether the data may be used or not.

## <a name="ucpo8"> 8. Event-restricted data usage </a>
The data usage is allowed for the timespan of a realworld event.

## <a name="ucpo9"> 9. Use the data not more than N times </a>
Data access is limited to a certain number of accesses. The accesses are counted and restricted with the help of an IDS Data App and the policy enforcement points (hooks to the UC APP) implemented in it and the Usage Control App. The actual "use" can be determined more finely by means of defined actions.

## <a name="ucpo10"> 10. Allow use of data and delete it after </a>
This policy class describes that an IDS app is authorized to use the data, but the data must be deleted after processing or display. In particular, the time of deletion must be described in much detail. For example, deletion can take place directly after a single use/processing, after multiple processing, or after a specified period of time. 
A suggestion for the definition of "use" at this point would be, for example, the fulfillment of a predefined action in an IDS Data App.

## <a name="ucpo11"> 11. Modify data (in transit) </a>
When using and processing data in one or more IDS Data Apps, data may need to be modified in some way before it can be used or processed. The modification of the data must occur during transmission before it is processed or displayed by an IDS Data App. This class of usage control policies can be used to describe the use of IDS Apps that require prior data modification in transit. In order for the data to be modified during transmission, a usage control framework is used to intercept the data and implement the required modifications for the use of the data in one or more IDS Data Apps.

## <a name="ucpo12"> 12. Log the data usage information </a>
Data access should be logged. The logging can be realized via the usage control app. The UC APP can log locally or log to a central location, such as the IDS Clearinghouse.

## <a name="ucpo13"> 13. Notify a party or a specific group of users when the data is used </a>
If someone needs to be informed whenever the data is processed by an IDS Data App this policy can be used. The usage control app will take care of the notification process.
For example a data consumer can send a notification to the data provider whenever the data is processed or transfered by an IDS Data App.

## <a name="ucpo14"> 14. Attach Policy when distribute to a third-party (Connector or route layer) </a>
If data is shared with third parties, a data provider may specify additional data usage policies to apply to third parties. Accordingly, the data consumer is obligated to forward the specified usage control policy to third parties, and only after successfull consent will the actual data be transported.

## <a name="ucpo15"> 15. Distribute only if encrypted (App, route layer) </a>
In a common scenario, a data provider specifies a policy to grant permission to one or more data consumers to use the data. In addition, there may aslo be cases where the Data Consumer requests permission to share the data with other users or third parties.
This class of policy deals exclusively with the state of the data asset in the case of exchange to third parties or further sharing. For example, one can specify a policy of this type to restrict the data consumer to share the data only if it is encrypted.

## <a name="ucpo16"> 16. Restrict the data usage to specific state (connector) </a>
This policy class can be used to restrict the usage based on a specific state change of the environment but not on the data asset state. The environment consists of contracts regarding the exchanged data and the connector itself. In particular, when there is a state change around a paticular contract, such as when the contract is unexpectedly terminated, this policy takes effect. In addition to contracts, state changes on the part of the connector are also covered in this policy class, e.g., when the firewall state of the connector changes.
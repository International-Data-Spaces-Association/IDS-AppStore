---
layout: default
title: Data App
nav_order: 5
description: ""
permalink: /data-app
---

# Data App
{: .fs-9 }

An IDS App is a re-usable asset. It can be downloaded from a central IDS App Store and it can be deployed and monitored by an IDS Connector.
{: .fs-6 .fw-300 }

## General

So that apps can be operated and executed in a secure environment, they must be made available as virtualized containers.

In principle, a distinction can be made between two different categories of data apps. The apps that fall under the category of system adapters have the task of connecting all types of data sources or data sinks and making them available to the underlying connector. This includes, for example, connections to databases, web services or Big Data systems. Furthermore, system adapters can be used to transform data models from connected data sources into a standardized format. In addition, system adapters can be used to enrich connected data with additional metadata.

Unlike system adapters, the category of smart data apps includes all applications that manipulate the data flow within a connector in some way. The main tasks of a smart data app include processing or transforming data. Examples include applications for data quality assurance or machine learning. The data that a smart data app processes and makes available again is usually already enriched with metadata.

IDS Data Apps are seen in the International Data Space as independent, functional and re-usable assets that can be deployed, executed and operated on a connector and its underlying data.
The primary goal of a data app is to process or provide data and data streams. This allows to extend the connector's functionality by adding additional data sources / sinks and data processing capabilities. IDS Data Apps can be distributed over the IDS Appstore to install them on the connector. In general the apps are packaged and distributed in a virtualized container to meet the requirements of a secure execution environment.

# Container Format
As mentioned earlier, apps are deployed and distributed in a virtualized container. The containers used should follow the OCI specification for building their images. The use of OCI compliant containers and runtime environments is not tied to a specific vendor. Due to the large operating system coverage and the open ecosystem, a high portability and interoperability between already existing connector implementations can be achieved.

Due to the diversity of existing connectors, app containers are described according to the OCI standard (Open Container Initiative). In addition to support for a large number of operating systems, this also prevents independence from commercial providers and binding to a specific provider.
Especially the offer of different runtime environments following the OCI standard enables a high degree of portability. Thus, an app can be run on a virtualized server as well as in the care of the large cloud platforms without any adaptations.


## Management and Orchestration
Within the IDS Connector, the underlying data flow is managed and orchestrated via middleware (e.g., Camel) by creating and defining routes for the different data. So each Connector is in control of where and how a data flow is defined within its boundaries. IDS Data Apps are also meant to work on the connectors data flow and therefore the can be integrated in the routes as well. It is possible to use more than one app at a time within a defined data route. The apps are specified in such a way they can be connected to each other using their input and ouput data endpoints to work on a data flow within a route. If several apps are connected to each other, we can speak of a processing chain to fulfill a specific purpose. 

The steps necessary for creating and operating a route with or without IDS Data Apps included are taken over by the connector (Configuration Manager) itself. Therefore IDS Data Apps are a part of the data routes to extend the connectors and the middlewares capabilities to connect data sources / data sinks or to process data.

# App Orchestration
Within the Connector, the application of several apps on one date should be made possible. Accordingly, the apps are designed in such a way that they can be connected to each other using their input and output interfaces. If several apps are connected to each other, we can speak of a processing chain. The steps necessary for creating and operating a processing chain are taken over by the connector itself. This means, among other things, that the control of the data flow between two or more apps is managed by the connector via message flows (e.g., Camel routes) to be defined. 

In order to use the execution of several apps on one date within a Connector environment, the apps are designed in such a way that you can join apps together to form processing chains. Orchestration between apps can be controlled and configured through the Connector using message flows.
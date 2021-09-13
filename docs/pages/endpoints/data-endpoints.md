---
layout: default
title: Data Endpoints
nav_order: 1
description: ""
permalink: /endpoints/data-endpoints
parent: Endpoints
---

# Data Endpoints
The endpoints for exchanging data between apps and between apps and connectors are divided into two categories. In each case, consuming data or delivering data is considered.
In principle, there is a data input endpoint for consuming data, which can be used to transport data to an app, and a data output endpoint for making data available from an app.

In order to identify more quickly whether data endpoints only communicate internally, especially within a connector environment, or whether communication is established with an external component, the data endpoints are separated once again.

- Input
- Input_External
- Output
- Output_External

## <a name="input"> Input </a>
The input endpoint is mandatory for almost all app types that work with data or data streams. The input endpoint is used to describe the consumption of data or the data flow between data apps and data apps and a connector. In summary, the data input endpoint describes an interface for all app types at which data can be transported to an app within the conncetors environment.

## <a name="input_ext"> Input External </a>
If an input endpoint is used to connect external data sources or data streams outside the actual connector environment, the External input endpoint can be used to make it clear in the metadata description that a connection is established across the boundaries of the connector at this endpoint.
This special form of the input endpoint is especially relevant for system adapter apps (data sink) that establish a connection to an external data sink and enable the storage of data in external systems.

## <a name="output"> Output </a>
The output endpoint is also mandatory for almost all app types that transfer data or data streams to other data apps or connectors. The Output Endpoint is used to describe the provisioning of (processed) data or data flows between Data Apps or Data Apps and a Connector. In summary, for all app types, the data output endpoint describes an interface where data can be consumed within the connector environment by other apps or the connector itself.

## <a name="output_ext"> Output External </a>
In addition to the data output endpoint already described for internal communication, there is also an output external endpoint. With this subcategory, it can already be specified during the creation of the metadata description that communication beyond the boundaries of the connector is created at this endpoint. This special form of output endpoint is primarily relevant for system adapter apps (data source) that establish a connection to an external data source and enable the reading of data in external systems.
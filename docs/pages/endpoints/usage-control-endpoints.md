---
layout: default
title: Usage Control Endpoints
nav_order: 3
description: ""
permalink: /endpoints/usage-control-endpoints
parent: Endpoints
---

# Usage Control Endpoints

## <a name="usage"> Usage </a>
The usage endpoint acts as a Policy Information Point (PIP) to deliver some information about the app itself represented in a json format.
The information that can be retrieved from the app at this endpoint contains general information for use with a usage control app.

The following information can then be retrieved via this Policy Information Point:
- **AppName:** The name of the app
- **AppURI:** The URI of the app or another identifier
- **AppPurpose:** The general purpose of an app.
- **AppActions:** The superclass of a policy: Usage, Distribute, Execute.

For more information on the usage control endpoint or the usage control integration patterns take a look at the usage-control document [configuration](../usage-control)
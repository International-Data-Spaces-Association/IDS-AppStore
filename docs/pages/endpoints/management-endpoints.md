---
layout: default
title: Management Endpoints
nav_order: 2
description: ""
permalink: /endpoints/management-endpoints
parent: Endpoints
---

# Management Endpoints

## <a name="config"> Config </a>
The configuration endpoint can be used to actively set or change configuration parameters during the runtime of an IDS Data App. The dynamic configuration method can be used to react to the behavior of the app depending on the state of the environment. A further description of the configuration options can be found in the [configuration](../configuration) document.

## <a name="status"> Status </a>
The status endpoint can but does not have to be implemented by an IDS Data App. The status endpoint can be used to retrieve status information from a data app during runtime. A minimal example of a status endpoint can be a so-called "keep-alive" or "ping" information, which can be used to implement a healtcheck of an app to check if it is currently available and running.
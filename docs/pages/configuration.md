---
layout: default
title: Configuration
nav_order: 8
description: ""
permalink: /configuration
---

# Data App Configuration

The IDS Data Apps can be configured for their use on a Connector, if required. Configuration can be used to set special application parameters or runtime configurations for an app. When configuring a Data App, a distinction can be made between two points in time of the configuration. Either apps can already be configured at deployment time with a static configuration file, or apps can alternatively be configured at runtime.

Which of the two variants of the configuration are used depends on the parameter to be set. All parameters for starting an app are essentially set using a static configuration. An example of such parameters are volume or file bindings or port mappings.  
With dynamic configuration, all parameters can be set that have an influence on the execution of an app. These can be method parameters, for example, which can be changed dynamically.

## <a name="static-conf"> Static Configuration (deployment) </a>
If an app is configured statically, this is done at deployment time. Typically, a configuration file is loaded into the app container or the container is preconfigured with special arguments before starting it up. The static configuration can also be made by the configuration manager when deploying an app on a connector. The configurations set at deploy time are usually not changed during app execution. 

![Data App Configuration Static](./assets/images/app-configuration-static.png)

## <a name="dynamic-conf"> Dynamic Configuration (runtime) </a>
For an app to support dynamic configuration, a config endpoint must be implemented to provide the configuration capability.
Configuration parameters that are addressed to the app during runtime via a provided config endpoint behave differently. These configuration parameters can be changed at any time by calling the config endpoint as soon as the app has been started. 

![Data App Configuration Dynamic](./assets/images/app-configuration-dynamic.png)
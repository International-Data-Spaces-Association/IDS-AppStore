---
layout: default
title: Configuration of App Store
nav_order: 2
permalink: /deployment/configuration-of-app-store
parent: Deployment
---

# Configuration of App Store

To link the App Store to the Harbor Registry, the `src/main/resources/application.properties` file must be configured. Adjust the following properties according to your registry URL:

* The container registry hostname, use the **App Store hostname** <br> `registry.host=appstore.example.org`
* The container registry url, use the **App Store URL** <br> `registry.url=https://appstore.example.org`
* The container registry project to use for this appstore instance <br> `registry.project=projectName`
* The container registry api url <br> `registry.api.url=https://appstore.example.org/api/v2.0`
* The container registry api username <br> `registry.client.user=apiUser`
* The container registry api password <br> `registry.client.password=password`

You need to change `appstore.example.org` to your **Harbor Registry URL**.  

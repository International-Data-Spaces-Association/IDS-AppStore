---
layout: default
title: Configuration of App Store
nav_order: 2
permalink: /deployment/configuration-of-app-store
parent: Deployment
---

# Configuration of App Store

To link the App Store to the Harbor Registry, the `src/main/resources/application.properties` file must be configured. Adjust the following properties according to your registry URL:

* The contaier registry hostname: `registry.host=appstore.example.org` 
* The container registry url: `registry.url=https://appstore.example.org` 
* The container registry project to use for this appstore instance: `registry.project=ids-binac` 
* The container registry api url: `registry.api.url=https://appstore.example.org/api/v2.0` 
* The container registry api username: `registry.client.user=apiUser` 
* The container registry api password: `registry.client.password=password` 

You need to change `appstore.example.org` to your registry URL.  
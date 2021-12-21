---
layout: default
title: Configuration of Harbor Registry and App Store
nav_order: 1
permalink: /deployment/configuration
parent: Deployment
---

# Configuration of Harbor Registry and App Store

## Configuration of Harbor Registry

If the Harbor Registry is well installed, you can access it via the link <https://myHaborDomainName>, replacing `myHaborDomainName` with the DNS of your Harbor Registry.

The default username is **admin** and the default password is **Harbor12345**. 

You can create new users and new projects, but we will use the default project library and default user credentials. The most important thing to configure is the link to the appstore webhook. Once you have clicked on the library project, click on the Webhooks tab where you need to specify a name and the endpointURL of the App Store, which should be in the following format: http://YourAppStoreDomainName/api/webhook/registry


## Configuration of the App Store

To link the App Store to the Harbor Registry, the `tsrc/main/resources/application` file must be configured as follows. You need to change `appstore.example.org` to your registry URL.  

* The contaier registry hostname: registry.host=appstore.example.org 
* The container registry url: registry.url=https://appstore.example.org 
* The container registry project to use for this appstore instance: registry.project=ids-binac 
* The container registry api url: registry.api.url=https://appstore.example.org/api/v2.0 
* The container registry api username: registry.client.user=apiUser 
* The container registry api password: registry.client.password=password 
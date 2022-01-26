---
layout: default
title: Configuration of Harbor Registry
nav_order: 1
permalink: /deployment/configuration-of-harbor
parent: Deployment
---

# Configuration of Harbor Registry

If the Harbor Registry is well installed, you can access it via the link <https://myHaborDomainName>, replacing `myHaborDomainName` with the DNS of your Harbor Registry.

The default username is **admin** and the default password is **Harbor12345**. 

You can create new users and new projects, but we will use the default project library and default user credentials. The most important thing to configure is the link to the App Store webhook. Once you have clicked on the library project, click on the webhooks tab where you need to specify a name and the endpointURL of the App Store, which should be in the following format: http://YourAppStoreDomainName/api/webhook/registry. 
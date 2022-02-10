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

You can create new users and new projects, but we will use the default project library and default user credentials. The most important thing to configure is the link to the App Store webhook. Once you have clicked on the library project, click on the webhooks tab where you need to specify a `name` and the `endpointURL` of the App Store, which should be in the following format: `http://YourAppStoreDomainName/api/webhook/registry.` 

It follows a step-by-step explanation: 

1. Login to the registry with your username and password. <br><br>
![Webhook Step 1](../assets/images/webhook-step-1.png)

2. Click on Projects. Then click on library, as the default project in the registry. You can also create another project. <br><br>
![Webhook Step 2](../assets/images/webhook-step-2.png)

3. Click on Webhooks to create a new webhook to the App Store. <br><br>
![Webhook Step 3](../assets/images/webhook-step-3.png)

4. Choose NEW Webhook. Add a name and an endpoint URL. The endpoint URL should have the following format: `<appstoreURL>/api/webhook/registry`. Change Notify Type to `http`. Adjust the other inputs as needed. <br><br>
![Webhook Step 4](../assets/images/webhook-step-4.png)

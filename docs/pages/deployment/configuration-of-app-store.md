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


# Deployment App Store with SSL Certificates

To have Appstore working with its full-functionalities, AppStore needs to be installed on a separate server with correct IDS certificate and an SSL certificate which is generated for this server with its specific domain name.  An SSL certificate can be created from any of the internet Certificate Authorities or maybe you can contact your network Admin if you have one at your company.

For enabling SSL for the Appstore server, we will use the folder deploy/local under the GitHub AppStore repo. Docker and Docker-compose will be used for deployment. You can using this link here to install docker and for docker-compose installation this link can be used. 

1. After finishing the configurations under application.properties, a docker image for this AppStore needs to be created. You can use the following command on the root directory of the AppStore directory:
```
docker build . -t appstoreimage:latest
```
2. Change the Appstore docker  image
3. Install IDS certificate under src/main/resources/conf folder and update values in config.json Productive mode and application.properties
4. Change the docker image in docker-composer
5. Insert you SSL certificates under deploy/local/SSL
6. Update some values in INGex.conf 
7. Run docker-compser 

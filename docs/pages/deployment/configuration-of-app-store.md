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


# Deployment App Store as a Standalone Server with HTTPS

To have Appstore working with its full-functionalities, AppStore needs to be installed on a separate server with correct IDS certificate and an SSL certificate which is generated for this server with its specific domain name.  An SSL certificate can be created from any of the internet Certificate Authorities or maybe you can contact your network Admin if you have one at your company.

For enabling SSL for the Appstore server, we will use the folder deploy/local under the GitHub AppStore repo. Docker and Docker-compose will be used for deployment. You can using this link [here](https://docs.docker.com/engine/install/) to install docker and for docker-compose installation [this link](https://docs.docker.com/compose/install/) can be used. 

1. After finishing the configurations under application.properties, a docker image for this AppStore needs to be created. You can use the following command on the root directory of the AppStore directory:
```
docker build . -t appstoreimage:latest
```
2. Replace the Appstore docker image in [docker-compose.yml](https://github.com/International-Data-Spaces-Association/IDS-AppStore/blob/main/deploy/local/docker-compose.yml) at line 68 with your genereated Appstore docker image, e.g., appstoreimage:latest will replace  `ghcr.io/international-data-spaces-association/ids-appstore:main` value, If the above docker command is used. 
 ```
    65 appstore:
    66 container_name: appstore
    67 hostname: appstore
    68 image: ghcr.io/international-data-spaces-association/ids-appstore:main
```
3. Install IDS certificate under [src/main/resources/conf](https://github.com/International-Data-Spaces-Association/IDS-AppStore/tree/main/src/main/resources/conf) folder and update the following values in config.json:
idsc:TEST_DEPLOYMENT will be replaced with idsc:PRODUCTIVE_DEPLOYMENT 
keystore-localhost.p12 to be replaced with your IDS certificate, e.g., appstrore.srv.com.p12
 and application.properties
4. Insert you SSL certificates under [deploy/local/SSL](https://github.com/International-Data-Spaces-Association/IDS-AppStore/tree/main/deploy/local/ssl) folder
5. Update some values in deploy/local/config/nginx.conf 
6. Run docker-compser 
 ```
 docker-compose up 
 ```
 7. Then, you will be access your app store sever with `https://yourAppStoreURL`

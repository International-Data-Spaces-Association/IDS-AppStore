---
layout: default
title: Configuration of App Store
nav_order: 2
permalink: /deployment/configuration-of-app-store
parent: Deployment
---

# Configuration of App Store

To link the App Store to the Harbor Registry, the `src/main/resources/application.properties` file must be configured. Adjust the following properties according to your registry URL:

* The container registry hostname, use the **Harbor Registry Hostname** <br> `registry.host=appstore.example.org`
* The container registry url, use the **Harbor Registry URL** <br> `registry.url=https://appstore.example.org`
* The container registry project to use for this appstore instance <br> `registry.project=projectName`
* The container registry api url <br> `registry.api.url=https://appstore.example.org/api/v2.0`
* The container registry api username <br> `registry.client.user=apiUser`
* The container registry api password <br> `registry.client.password=password`

You need to change `appstore.example.org` to your **Harbor Registry Hostname**.  


# Deployment App Store as an SSL/HTTPS Standalone Server

To have Appstore working with its full-functionalities, AppStore needs to be installed on a standalone server with a valid IDS certificate and a valid SSL certificate which are generated for this server with its specific domain name.  An SSL certificate can be created from any of the Internet Certificate Authorities or maybe you can contact your network/system admin at your company for this purpose.

For enabling SSL on Appstore server, we will use the folder deploy/local under the  [AppStore repo](https://github.com/International-Data-Spaces-Association/IDS-AppStore). Also, Docker and Docker-compose will be used for deployment. You can using this link [here](https://docs.docker.com/engine/install/) to install docker and for docker-compose installation [this link](https://docs.docker.com/compose/install/) can be used. 

It is assumed that have started with [Installation and Running of the App Store](https://international-data-spaces-association.github.io/IDS-AppStore/deployment#installation-and-running-of-the-app-store) steps and the above (Configuration of App Store) (https://international-data-spaces-association.github.io/IDS-AppStore/deployment/configuration-of-app-store#configuration-of-app-store) according to your required configurations. 


1. After finishing the configurations under application.properties, a docker image for this AppStore needs to be created. You can use the following command at the root  of the AppStore directory:
```
docker build . -t appstoreimage:latest
```
2. Replace the Appstore docker image in [docker-compose.yml](https://github.com/International-Data-Spaces-Association/IDS-AppStore/blob/main/deploy/local/docker-compose.yml) under deploy/local folder  at line 68 with your genereated Appstore docker image, e.g., appstoreimage:latest will replace  `ghcr.io/international-data-spaces-association/ids-appstore:main` value, If the above docker command is used where what comes after -t is referring to to the docker image name. 
 ```
    65 appstore:
    66 container_name: appstore
    67 hostname: appstore
    68 image: ghcr.io/international-data-spaces-association/ids-appstore:main
```
3. Install IDS certificate under [src/main/resources/conf](https://github.com/International-Data-Spaces-Association/IDS-AppStore/tree/main/src/main/resources/conf) folder and update the following values in config.json:
- idsc:TEST_DEPLOYMENT will be replaced with idsc:PRODUCTIVE_DEPLOYMENT 
- keystore-localhost.p12 to be replaced with your IDS certificate, e.g., appstrore.srv.com.p12
 and [application.properties](https://github.com/International-Data-Spaces-Association/IDS-AppStore/tree/main/src/main/resources/application.properties)
4. Insert you SSL certificate files under [deploy/local/SSL](https://github.com/International-Data-Spaces-Association/IDS-AppStore/tree/main/deploy/local/ssl) folder
5. Specify the location of your server certificate and you private in [nginx.conf](https://github.com/International-Data-Spaces-Association/IDS-AppStore/tree/main/deploy/local/config) under deploy/local/config/ folder
6. Run docker-composer command 
 ```
 docker-compose up 
 ```
 7. Then, you will be access your app store sever with `https://yourAppStoreURL`. Also, make sure that you allow the access to your server from outside, if it is running behind a firewall or a proxy server.

---
layout: default
title: Configuration of App Store
nav_order: 2
permalink: /deployment/configuration-of-app-store
parent: Deployment
---

# Configuration of App Store

To link the App Store to the Harbor Registry, the `src/main/resources/application.properties` file must be configured. Adjust the following properties according to your registry URL:

* The container registry hostname, use the *Harbor Registry Hostname* <br> `registry.host=registry.example.org`
* The container registry url, use the *Harbor Registry URL* <br> `registry.url=https://registry.example.org`
* The container registry project to use for this appstore instance <br> `registry.project=projectName`
* The container registry api url <br> `registry.api.url=https://registry.example.org/api/v2.0`
* The container registry api username <br> `registry.client.user=apiUser`
* The container registry api password <br> `registry.client.password=password`

You need to change `registry.example.org` to your *Harbor Registry Hostname*.  


# Deploy App Store as an SSL/HTTPS Standalone Server

For the App Store to work with all its functionalities, the App Store must be installed on a standalone server with a valid IDS certificate and a valid SSL certificate created for that server with its specific domain name. An SSL certificate can be created by an Internet Certificate Authority or you can contact your company's network/system administrator.

Docker and Docker-compose are used for the deployment described below. To install Docker, you can use this [link](https://docs.docker.com/engine/install/). To install Docker-compose, you can use this [link](https://docs.docker.com/compose/install/).

From here on, it is assumed that you have performed the steps of [installing and running the App Store](https://international-data-spaces-association.github.io/IDS-AppStore/deployment#installation-and-running-of-the-app-store) and [configuring the App Store](https://international-data-spaces-association.github.io/IDS-AppStore/deployment/configuration-of-app-store#configuration-of-app-store) as described above according to your settings and configurations.

1. After configuring the `application.properties` file as described in the previous section, you need to create a Docker image for the App Store. To do this, the following command can be used in the root of your App Store directory:
```
docker build . -t appstoreimage:latest
```
`appstoreimage:latest` is the name of the Docker image. Replace it with a name of your choice.

2. Under `deploy/local/docker-compose.yml` of your local App Store repository, replace the App Store Docker image in line 68 with the App Store Docker image that you created in the previous step.
In the example given in this documentation, the value `ghcr.io/international-data-spaces-association/ids-appstore:main` must be replaced with `appstoreimage:latest`, the name of the Docker image created in the first step.
 ```
    65 appstore:
    66 container_name: appstore
    67 hostname: appstore
    68 image: ghcr.io/international-data-spaces-association/ids-appstore:main
```

3. Install the IDS certificate in the `src/main/resources/conf` folder and update the following values in the config.json file:
- Replace `idsc:TEST_DEPLOYMENT` with `idsc:PRODUCTIVE_DEPLOYMENT`.
- Replace `keystore-localhost.p12` with your IDS certificate.

Other values in the `application.properties` file must be updated as follows:
 - `configuration.keyStorePassword=password`: Replace `password` with your IDS certificate password.
 - `idscp2.keystore=./src/main/resources/conf/keystore-localhost.p12`: Replace `keystore-localhost.p12` with the name of your IDS certificate.
 - `server.ssl.enabled=true`: Replace `true` with `false` to disable the default setting of enabling SSL using the IDS certificate, as it is replaced by the SSL certificate configuration.

4. Place your SSL certificate files in the `deploy/local/SSL` folder. Delete the existing `server.cert` and `server.key` files. Rename your SSL certificate to `server.cert` and your private key to `server.key`.

5. Run the following docker-compose command:
 ```
 docker-compose up 
 ```

6. Your App Store's user interface can be accessed via `https://yourAppStoreHostname`, where `yourAppStoreHostname` is replaced with your App Store's domain name. Your App Store's APIs can be accessed via `https://yourAppStoreHostname/api`. Your App Store's Swagger UI can be accessed via `https://yourAppStoreHostname/api/docs`. Make sure that you allow outside access to your server if it is running behind a firewall or proxy server.

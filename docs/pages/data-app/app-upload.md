---
layout: default
title: App Upload
nav_order: 2
permalink: /data-app/app-upload
parent: Data App
---

# App Upload 

### Requirements before uploading an app
1. Docker software must be installed on your machine. Docker's installation procedure can be found [here](https://docs.docker.com/engine/install/).
2. Python3 and PiP3 are required to use the Python script to upload an app. The installation of Python3 can be found [here](https://realpython.com/installing-python/).
3. Your application should be uploaded to Docker Hub. For this, you will need a Docker Hub account. You can sign up for a new account [here](https://hub.docker.com/signup). **Optionally**, your application can be created on your local machine.

### Dockerizing your app
1. Your app must be in a Docker format, as this is currently the only accepted format. In the future, other formats will also be allowed. Instructions for creating a Docker file can be found at the following link: <https://docs.docker.com/develop/develop-images/dockerfile_best-practices/>
2. You need to have your Docker image on Docker Hub. To do this, you need to log in to [docker site](https://hub.docker.com/) and create a new public repository with your chosen name. You will receive the push command `docker push username/test:tagname` given that `username` is your username.
3. Once both steps have been performed, the image can be build and pushed to Docker Hub using the following commands: `docker build . -t {yourImage:tag, e.g., username/test:tagname}` and `docker push {yourImage:tag, e.g., username/test:tagname}`. Docker Hub will ask for your credentials. 

### Uploading process
Currently, an app is uploaded using a [Python script](https://github.com/International-Data-Spaces-Association/IDS-AppStore/blob/main/scripts/tests/create_resources_and_upload_app_local.py). To upload an app using the Python script, perform the following three steps:

1. Make sure that you have the Python packages `docker` and `requests` installed. You can install them using the following commands:
```
pip3 install docker
pip3 install requests  
```

2. Before running the Python script [create_resources_and_upload_app_local.py](https://github.com/International-Data-Spaces-Association/IDS-AppStore/blob/main/scripts/tests/create_resources_and_upload_app_local.py), the following information must be updated according to your settings. <br><br> You may need to update the settings of the Connector API. The Connector API settings are set by default as shown below. Your Connector API settings can be found in the `application.properties` file of the Dataspace Connector. 
```python
##########################
# APPSTORE API SETTINGS #
##########################
apiUser = "admin"
apiPassword = "password"
host = "localhost"
port = 8080
```
Adjust the App Store registry settings, which are the settings of Harbor if you followed the usual installation procedure. 
As part of the [App Store configuration](https://international-data-spaces-association.github.io/IDS-AppStore/deployment/configuration-of-app-store), 
you have already set the corresponding information in the `application.properties` file of the App Store.
```python
########################################
# APPSTORE REGISTRY SETTINGS (HARBOR)  #
########################################
registry_address = "app.registry.example.org"
registry_repo_name = "library"
registry_user = "admin"
registry_password = "password"
```
Update the Docker settings. If the given image is not found locally, it will be cloned from the Docker Hub.
```python
###################
# DOCKER SETTINGS #
###################
resource_id_tag_version = "latest"
image_name = "ahemid:idsapp"
resource_version = 1
```
Lastly, metadata of the app to be uploaded must be specified. For this, please update the JSON objects in the methods given below (section `APP METADATA METHODS` in the Python file). <br><br> Individualize the `create_resource()` method. Specify the title of the resource and give a resource description. List keywords for the search mechanism. Provide information about the publisher and the owner of the data, the resources's license, and the abbreviation of the language used. Please find information about the range of the object properties in the IDS Information Model documentation: <https://international-data-spaces-association.github.io/InformationModel/docs/index.html>. Information about classes can be found there as well.
```python
def create_resource():
    json = {
        "title": "DataProcessingApp", 
        "description": "data app for processing data.",
        "keywords": [ 
            "data",
            "processing",
            "fit"
        ],
        "publisher": "https://fit.fraunhofer.de",
        "sovereign": "https://fit.fraunhofer.de",
        "language": "EN",
        "license": "https://www.apache.org/licenses/LICENSE-2.0",
        "paymentMethod": "free" 
    }
    loc = post_request_check_response(f"{combined_host}/api/resources", json)
    return loc
```
Update the `create_representation()` method.
```python
def create_representation():
    json = {
        "title": "Docker Representation",
        "description": "This is the docker representation for the DataProcessingApp",
        "language": "EN",
        "runtimeEnvironment": "docker",
        "distributionService": "https://localhost:8080"
    }
    loc = post_request_check_response(f"{combined_host}/api/representations", json)
    return loc
```
Adjust the `create_dataApp()` method. 
```python
def create_dataApp():
    json = {
        "title": "DataApp Information",
        "description": "This is the dataApp information for the DataProcessingApp.",
        "docs": "App-related human-readable documentation.",
        "environmentVariables": "Env1=environmentvariable;Env2=environmentvariable2",
        "storageConfig": "/data/temp:/temp",
        "supportedUsagePolicies": [
            "PROVIDE_ACCESS"
        ]
    }
    loc = post_request_check_response(f"{combined_host}/api/apps", json)
    return loc
```
[Endpoints](https://international-data-spaces-association.github.io/IDS-AppStore/endpoints) can be created using the `create_endpoints()` method. Here, an example of an input endpoint is given. 
```python
def create_endpoints():
    json = {
        "title": "DataApp Input Endpoint",
        "description": "This is the input endpoint for the DataProcessingApp.",
        "location": "/input",
        "mediaType": "application/json",
        "port": 5000,
        "protocol": "HTTP/1.1",
        "type": "Input",
        "docs": "https://app.swaggerhub.com/apis/app/1337",
        "info": "More information about the endpoint",
        "path": "/input"
    }
    loc = post_request_check_response(f"{combined_host}/api/endpoints", json)
    return loc
```
The artifact is an instance of a representation materialized at a partiuclar version and point in time. Give the title and the description for the artifact.
```python
def create_artifact():
    json = {
        "title": "DataApp Template",
        "description": "This is the template for the DataProcessingApp",
        "value": ""
    }
    loc = post_request_check_response(f"{combined_host}/api/artifacts", json)
    return loc
```


3. To upload your app, running the following command:
`python3 create_resources_and_upload_app.py`
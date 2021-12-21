---
layout: default
title: App Upload
nav_order: 2
permalink: /deployment/app-upload
parent: Deployment
---

# App Upload 

**Requirements before uploading an app**
1. Docker software must be installed on your machine. Docker's installation procedure can be found
[here](https://docs.docker.com/engine/install/).
2. Python3 and PiP3 are required to use the Python script to upload an app. The installation of Python3 can be found [here](https://realpython.com/installing-python/).
3. Your application should be uploaded to Docker Hub. For this, you will need a Docker Hub account. You can sign up for a new account [here](https://hub.docker.com/signup).


**Dockerizing your app**
1. Your app must be in a Docker format. Instructions for creating a Docker file can be found at the following link: 
https://docs.docker.com/develop/develop-images/dockerfile_best-practices/
2. You need to have your Docker image on Docker Hub. To do this, you need to log in to [docker site](https://hub.docker.com/) and create a new public repository with your chosen name. You will receive the push command `docker push username/test:tagname` given that username is your username.
3. Once both steps have been performed, the image can be build and pushed to Docker Hub using the following commands: 
`docker build . -t {yourImage:tag, e.g., username/test:tagname}` and
`docker push {yourImage:tag, e.g., username/test:tagname}`. Docker Hub will ask to input your credentials. 

**Uploading process**
1. Before running the [Python script](https://github.com/International-Data-Spaces-Association/IDS-AppStore/blob/main/scripts/tests/create_resources_and_upload_app_local.py), make sure that you have python "docker, requests" installed. You can install them using the following commands:
```
pip3 install docker
pip3 install requests  
```
2. You need to update the required information in the Python script [create_resources_and_upload_app.py](https://github.com/International-Data-Spaces-Association/IDS-AppStore/blob/main/scripts/tests/create_resources_and_upload_app_local.py). They are tagged with #TOChange.
3. To upload your app, running the following command:
`python3 create_resources_and_upload_app.py`
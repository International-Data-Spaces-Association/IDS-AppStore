---
layout: default
title: Deployment
nav_order: 4
permalink: /deployment
has_children: true
has_toc: true
---

# Deployment
{: .fs-9 }

**This section is currently being edited.** In the future, this section will include guidance on configuring and deploying the IDS App Store. 
{: .fs-6 .fw-300 }

There are three main components needed to make the entire App Store environment running, starting from uploading an app to the App Store to downloading an app from the Connector.  

Those main components are as follows: 
* Harbor Registry  
* App Store 
* Connector and Portainer 

It follows instructions on how to install each component.

## Installation  and Running of the Harbor Registry
It is advisable to have the registry install on a machine which have a domain name, not on localhost. Here the core instructions to install the Harbor Registry is given, using `sudo`. The full tutorial can be found under the following link: <https://goharbor.io/docs/1.10/install-config/>

1. **Download**: 
Download the latest Harbor release from the following website: <https://github.com/goharbor/harbor/releases> (currently version 2.4.1)

2. **Extract**: 
Extract the downloaded file: `sudo tar xvf harbor-offline-installer-version.tgz`. 
If there is an old installation, remove the old harbor data folders: `rm -r /data/database rm -r /data/registry` 

3. **Configure the Harbor yml file**: 
Edit `harbor.yml.tmpl` and rename it to `harbor.yml`. The parameters take effect when you run the `install.sh` script to install or reconfigure Harbor.

4. **Run the installer script**:
Run the following commands:
```
sudo ./prepare 
sudo ./install.sh
```

## Installation and Running of the App Store
The App Store can be installed from the following GitHub repository: <https://github.com/International-Data-Spaces-Association/IDS-AppStore>

Instruction on how to install and run the App Store can be found [here](./getting-started).


## Installation and Running of the Connector and Portainer: 

The Dataspace Connector can be installed via the following link: <https://github.com/International-Data-Spaces-Association/DataspaceConnector>. Installation instruction are provided. 

If both the Connector and the App Store are installed on the same machine, the running port must be changed in the `src/main/resources/application.properties` file. 8080 must be replaced with the number you choose, e.g., 8085.   

Portainer can be installed via the following link: <https://docs.portainer.io/v/ce-2.9/start/install/agent/docker>.
The Connector is configured to connect to portainer on port 9000.





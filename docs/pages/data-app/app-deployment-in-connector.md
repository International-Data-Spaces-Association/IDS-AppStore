---
layout: default
title: App Deployment in Connector
nav_order: 3
permalink: /data-app/app-deployment-in-connector
parent: Data App
---

# App Deployment in Connector
{: .fs-9 }

**This section is currently being edited.** In the future, this section will include guidance on performing actions on your configured connector. 
{: .fs-6 .fw-300 }

---

The following actions must be perfomed on your configured connector. The connector can be accessed using your assigned URL. For the examples shown in this documentation, the connector is accessed using <https://localhost:8080>. In addition, you must replace the App Store domain name in the examples with your domain name.

### Step 1: Configuration
For setting custom configurations, the `application.properties` of the Dataspace Connector must be modified. 

### Step 2: Create App Store Entiry in Connector
An App Store entity can be ceated via `POST /api/appstores`: 

```
curl -X 'POST' \ 
  'https://localhost:8080/api/appstores' \ 
  -H 'accept: */*' \ 
  -H 'Content-Type: application/json' \ 
  -d '{ 
  "title": "Example AppStore", 
  "description": "Some description", 
  "location": "https://binac.fit.fraunhofer.de/api/ids/data" 
}' 
````

### Step 3: Query IDS App Store
To query the apps provided by the App Store, you can use the `POST api/ids/description endpoint`. This will allow you to view the various offerings.


### Step 4: App Metadata Download from the IDS App Store
To download an app, you need the ID of the `ids:AppResource`. With this and the ID of the app store entity that you previously registered via the REST API, you can then download the app's metadata.


### Step 5: Deploy and Maintain IDS Apps
The endpoint `PUT /api/apps/{id}/actions` can be used to perform actions for downloaded apps.
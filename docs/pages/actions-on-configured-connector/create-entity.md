---
layout: default
title: Create Entity
nav_order: 1
permalink: /actions-on-configured-connector/create-entity
parent: Actions
---

# Create App Store Entity in Connector 

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
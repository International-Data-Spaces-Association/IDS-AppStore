---
layout: default
title: Metadata Representation Endpoints
nav_order: 3
description: ""
permalink: /endpoints/metadata-representation-endpoints
parent: Endpoints
---

# <a name="infomodel-endpoint-ref"> Endpoints - Metadata Representation (IDS-Informationmodel) </a>
Due to the free choice in the programming languages and frameworks used in the development of IDS apps, endpoints must be described very precisely in terms of the communication protocols used and the data schemas employed. It is also possible to integrate multiple protocols from one app with multiple endpoints for communication in parallel.

For each communication protocol used in an app, a so-called EndpointBinding must be created in the metadata description, which, in addition to the protocol used, also represents the affiliation of the implemented endpoints to a protocol by means of endpoint references.

Defined standards already exist for detailed descriptions of communication endpoints, which can be used at this point. For the description of HTTP interfaces, for example, reference can be made to an app-specific OpenApi specification; equivalently, the AsyncApi specification exists for the description of asynchronous interfaces. If, for some reason, existing formats cannot be used, it is also possible to specify a specially defined representation. Preferably, however, the use of already existing specifications is recommended at this point.

An endpoint reference consists of an endpoint type that classifies the endpoint according to the categories described above. In addition, the endpoint reference addresses a section of the supplied interface description via JSON reference for a detailed and specified representation regarding port, path mapping and the used data schema of the respective endpoint. 

## Example Endpoint binding for an Input Endpoint:
```javascript
    # Endpoint bindings
	ids:endpointBinding [
	    a ids:EndpointBinding ;            
            # EndpointBinding defined as a reference to an (external) specification for interface descriptions (e.g. OpenApi, AsyncApi)
            # Provide Endpoint Specification from an URI
			ids:apiSpecReference <https://example.org/myOpenAPISpec.json> ;
				
            # Provide Endpoint Specification embedded as raw data
            # Can be used as an alternative to ids:apiSpecReference property, if referencing a (external) resource is not desired or possible				
			ids:apiSpec "raw data of the api specification." ;
			
            # Define a version of the referenced specification if it changes over time
            ids:apiSpecVersion "0.1" ;

            # Definition of the used endpoint protocol 
            ids:protocolType idsc:HTTP ;
  		
            # EndpointReference to describe different endpoint types
            # Endpoints are referenced by providing a JSON-reference to a specific section of the provided specification            
			# In addition to the specification reference the endpoint type is classified by the categories mentioned above
			ids:endpointReference [
			    a ids:EndpointReference ;
				    ids:value "https://example.org/myOpenAPISpec.json#/myElement";
					ids:appEndpointType idsc:INPUT_ENDPOINT ;
				] ;
  			];
```
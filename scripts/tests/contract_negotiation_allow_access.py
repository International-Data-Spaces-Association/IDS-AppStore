#
# Copyright 2020 Fraunhofer Institute for Software and Systems Engineering
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

import requests
import pprint
import json
import tqdm

# Suppress ssl verification warning
requests.packages.urllib3.disable_warnings()

s = requests.Session()
s.auth = ("admin", "password")
s.verify = False

###############################################################################
# CREATE
###############################################################################

def create_catalog():
    response = s.post("https://localhost:8080/api/catalogs", json={})
    return response.headers["Location"]


def create_offered_resource():
    response = s.post("https://localhost:8080/api/resources", json={
            "title": "DataProcessingApp",
            "description": "data app for processing data.",
            "keywords": [
                "data",
                "processing",
                "fit"
            ]
    })
    return response.headers["Location"]


def create_representation():
    response = s.post("https://localhost:8080/api/representations", json={
            "runtimeEnvironment": "docker",
            "distributionService": "https://binac.fit.fraunhofer.de"
    })
    return response.headers["Location"]

def create_app():
    response = s.post("https://localhost:8080/api/apps", json={
        "docs": "App-related human-readable documentation.",
        "title": "Smart Data App for Example Usage",
        "environmentVariables": "Env1=environmentvariable;Env2=environmentvariable2",
        "description": "data app for processing data.",
        "storageConfig": "/data/temp:/temp",
        "supportedUsagePolicies": [
            "PROVIDE_ACCESS"
        ]
    })
    return response.headers["Location"]


def create_valid_endpoints():
     response = s.post("https://localhost:8080/api/endpoints", json={
         "location": "/input",
         "path": "/input",
         "mediaType": "application/json",
         "port": 5000,
         "protocol": "HTTP/1.1",
         "type": "Status",
         "docs": "https://app.swaggerhub.com/apis/app/1337",
         "info": "Endpoint-related human-readable information"
     })
     return response.headers["Location"]

def create_test_endpoints():
    response = s.post("https://localhost:8080/api/endpoints", json={
        "location": "/output",
        "path": "/output",
        "mediaType": "application/json",
        "port": 5000,
        "protocol": "HTTP/1.1",
        "type": "Input",
        "docs": "https://app.swaggerhub.com/apis/app/1337",
        "info": "Endpoint-related human-readable information"
    })
    return response.headers["Location"]

def create_artifact():
    response = s.post(
        "https://localhost:8080/api/artifacts", json={"value": "SOME APP TEMPLATE"}
    )
    return response.headers["Location"]

def create_contract():
    response = s.post(
        "https://localhost:8080/api/contracts",
        json={
            "start": "2021-04-06T13:33:44.995+02:00",
            "end": "2021-12-06T13:33:44.995+02:00",
        },
    )
    return response.headers["Location"]


def create_rule_allow_access():
    response = s.post(
        "https://localhost:8080/api/rules",
        json={
            "value": """{

  "@context" : {

    "xsd" : "http://www.w3.org/2001/XMLSchema#",

    "ids" : "https://w3id.org/idsa/core/",

    "idsc" : "https://w3id.org/idsa/code/"

  },

  "@type" : "ids:Permission",

  "@id" : "https://w3id.org/idsa/autogen/permission/00f09a77-0f0f-474d-8198-5195ef55e0eb",

  "ids:title" : [ {

    "@value" : "Example Usage Policy",

    "@type" : "http://www.w3.org/2001/XMLSchema#string"

  } ],

  "ids:description" : [ {

    "@value" : "n-times-usage",

    "@type" : "http://www.w3.org/2001/XMLSchema#string"

  } ],

  "ids:action" : [ {

    "@id" : "https://w3id.org/idsa/code/USE"

  } ],

  "ids:constraint" : [ {

    "@type" : "ids:Constraint",

    "@id" : "https://w3id.org/idsa/autogen/constraint/e0a353a2-ef1d-4932-b3cf-a5a0a5a1455e",

    "ids:operator" : {

      "@id" : "https://w3id.org/idsa/code/LTEQ"

    },

    "ids:leftOperand" : {

      "@id" : "https://w3id.org/idsa/code/COUNT"

    },

    "ids:rightOperand" : {

      "@value" : "5",

      "@type" : "xsd:double"

    }

  } ]

}"""
        },
    )
    return response.headers["Location"]

###############################################################################
# LINKING
###############################################################################
def add_resource_to_catalog(catalog, resource):
    response = s.post(catalog + "/resources", json=[resource])

def add_catalog_to_resource(resource, catalog):
    response = s.post(resource + "/catalogs", json=[catalog])

def add_representation_to_resource(resource, representation):
    response = s.post(resource + "/representations", json=[representation])

def add_endpoints_to_app(app, endpoints):
    response = s.post(app + "/endpoints", json=[endpoints])

def add_app_to_representation(representation, app):
    response = s.post(representation + "/apps", json=[app])

def add_artifact_to_representation(representation, artifact):
    response = s.post(representation + "/artifacts", json=[artifact])

def add_contract_to_resource(resource, contract):
    response = s.post(resource + "/contracts", json=[contract])

def add_rule_to_contract(contract, rule):
    response = s.post(contract + "/rules", json=[rule])

############################################################################
# IDS
############################################################################
def descriptionRequest(recipient, elementId):
    url = "https://localhost:8080/api/ids/description"
    params = {}
    if recipient is not None:
        params["recipient"] = recipient
    if elementId is not None:
        params["elementId"] = elementId

    return s.post(url, params=params)


def contractRequest(recipient, resourceId, artifactId, download, contract):
    url = "https://localhost:8080/api/ids/contract"
    params = {}
    if recipient is not None:
        params["recipient"] = recipient
    if resourceId is not None:
        params["resourceIds"] = resourceId
    if artifactId is not None:
        params["artifactIds"] = artifactId
    if download is not None:
        params["download"] = download

    return s.post(url, params=params, json=[contract])


# Create resources
catalog = create_catalog()
offers = create_offered_resource()
app = create_app()
endpoints = create_valid_endpoints()
endpointsTest = create_test_endpoints()
representation = create_representation()
artifact = create_artifact()
contract = create_contract()
use_rule = create_rule_allow_access()

# Link resources
add_resource_to_catalog(catalog, offers)

add_representation_to_resource(offers, representation)

add_endpoints_to_app(app, endpoints)

add_endpoints_to_app(app, endpointsTest)

add_app_to_representation(representation, app)

add_artifact_to_representation(representation, artifact)

add_contract_to_resource(offers, contract)
add_rule_to_contract(contract, use_rule)

# Call description
response = descriptionRequest("https://localhost:8080/api/ids/data", offers)
offer = json.loads(response.text)
pprint.pprint(offer)

# Negotiate contract
obj = offer["ids:contractOffer"][0]["ids:permission"][0]
obj["ids:target"] = artifact
response = contractRequest(
    "https://localhost:8080/api/ids/data", offers, artifact, False, obj
)
pprint.pprint(str(response.content))

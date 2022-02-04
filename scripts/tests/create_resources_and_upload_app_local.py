import pprint
import time

import docker
import requests
from requests import HTTPError

##########################
# APPSTORE API SETTINGS #
##########################
apiUser = "admin"
apiPassword = "password"
host = "localhost"
port = 8080
protocol = "https"
if port is not None:
    combined_host = f"{protocol}://{host}:{port}"
else:
    combined_host = f"{protocol}://{host}"

########################################
# APPSTORE REGISTRY SETTINGS (HARBOR)  #
########################################
registry_address = "app.registry.example.org"
registry_repo_name = "library"
registry_user = "admin"
registry_password = "password"

###################
# DOCKER SETTINGS #
###################
client = docker.from_env()

resource_id_tag_version = "latest"
image_name = "ahemid/idsapp"

resource_version = 1


############################
# APP METADATA METHODS     #
############################

def create_catalog():
    json = {}
    loc = post_request_check_response(f"{combined_host}/api/catalogs", json)
    return loc


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


def create_dataApp():
    json = {
        "title": "DataApp Information",
        "description": "This is the dataApp information for the DataProcessingApp.",
        "docs": "App-related human-readable documentation.",
        "environmentVariables": "dbUser=sa;dbPasswd=passwd",
        "storageConfig": "-v /data",
        "supportedUsagePolicies": [
            "PROVIDE_ACCESS"
        ]
    }
    loc = post_request_check_response(f"{combined_host}/api/apps", json)
    return loc


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


def create_artifact():
    json = {
        "title": "DataApp Template",
        "description": "This is the template for the DataProcessingApp",
        "value": ""
    }
    loc = post_request_check_response(f"{combined_host}/api/artifacts", json)
    return loc


def create_contract():
    json = {
        "start": "2021-04-06T13:33:44.995+02:00",
        "end": "2021-12-06T13:33:44.995+02:00",
    }
    loc = post_request_check_response(f"{combined_host}/api/contracts", json)
    return loc


def create_rule_allow_access():
    json = {
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
    }
    loc = post_request_check_response(f"{combined_host}/api/rules", json)
    return loc

##########################################################################
# USUALLY, IT SHOULD NOT BE NECESSARY TO CHANGE ANYTHING BELOW THIS LINE # 
##########################################################################


#############################
# LINKING RESOURCES METHODS #
#############################
def link_two_resources(resource1, resource2):
    json = [resource2]
    resource1_name = resource1.replace(f"{combined_host}", "").split("/")[2]
    resource2_name = resource2.replace(f"{combined_host}", "").split("/")[2]
    pprint.pprint(f"Adding links for {resource1_name} and {resource2_name}")
    post_request_check_response(f"{resource1}/{resource2_name}", json, True, False)


def add_resource_to_catalog(catalog, resource):
    link_two_resources(catalog, resource)


def add_catalog_to_resource(resource, catalog):
    link_two_resources(resource, catalog)


def add_representation_to_resource(resource, representation):
    link_two_resources(resource, representation)


def add_endpoint_to_app(app, endpoint):
    link_two_resources(app, endpoint)


def add_app_to_representation(representation, app):
    link_two_resources(representation, app)


def add_artifact_to_representation(representation, artifact):
    link_two_resources(representation, artifact)


def add_contract_to_resource(resource, contract):
    link_two_resources(resource, contract)


def add_rule_to_contract(contract, rule):
    link_two_resources(contract, rule)

####################
# REQUEST SETTINGS #
####################
requests.packages.urllib3.disable_warnings()
session_creds = requests.Session()
session_creds.auth = (apiUser, apiPassword)
session_creds.verify = False

session = requests.session()
session.verify = False


############################
# HTTP POST HELPER METHODS #
############################
def get_request_check_response(url, creds=True):
    try:
        if creds is True:
            response_tmp = session_creds.get(url)
            time.sleep(5)
            response_tmp.raise_for_status()
        else:
            response_tmp = session.get(url)
            time.sleep(5)
            response_tmp.raise_for_status()
        return response_tmp
    except HTTPError as http_error:
        pprint(f"HTTP ERROR OCCURED: {http_error}")
    except Exception as err:
        pprint(f"Something went wrong sending the request: {err}")


def post_request_check_response(url, json, creds=True, ret_location=True):
    if json is None:
        raise Exception(f"Problem with request json!, json= {json}")
    try:
        if creds is True:
            response_tmp = session_creds.post(url, json=json)
            response_tmp.raise_for_status()
        else:
            response_tmp = session.post(url, data=json)
            response_tmp.raise_for_status()

        if ret_location is True:
            loc = response_tmp.headers["Location"]
            if loc is None:
                raise Exception(f"Problem with response location!, requestUrl={url}")
            pprint.pprint(loc)
            return loc
        else:
            return response_tmp

    except HTTPError as http_error:
        pprint(f"HTTP ERROR OCCURED: {http_error}")
    except Exception as err:
        pprint(f"Something went wrong sending the request: {err}")


def post_description_request(recipient, element_id):
    params = {}
    if recipient is not None:
        params["recipient"] = recipient
    if element_id is not None:
        params["elementId"] = element_id
    try:
        response_tmp = session_creds.post(f"{combined_host}/api/ids/description", params=params)
        response_tmp.raise_for_status()
        return response_tmp
    except HTTPError as http_error:
        pprint(f"HTTP ERROR OCCURED: {http_error}")
    except Exception as err:
        pprint(f"Something WENT WRONG SENDING THE REQUEST: {err}")
    else:
        pprint(f"REQUEST SUCCESSFULL!")

	
#################
# DOCKER METHOD #
#################

def login_to_registry(registry_address_tmp, registry_user_tmp, registry_password_tmp):
    if registry_address_tmp is None:
        raise Exception("The registry address should not be null or empty")
    if registry_user_tmp is None:
        raise Exception("The registry user should not be null or empty")
    if registry_password_tmp is None:
        raise Exception("The registry user password should not be null or empty")

    try:
        response = client.login(username=registry_user_tmp, password=registry_password_tmp,
                                registry=registry_address_tmp)
        if response is not None:
            pprint.pprint(f"Successfully logged in to the registry. registry={registry_address_tmp}")
            pprint.pprint(f"registry_response: {response}")
            return response
        else:
            raise Exception("Failed to login to the registry.")
    except docker.errors.APIError as docker_error:
        pprint.pprint(f"Failed to login in to the registry. error={docker_error}")
        raise docker_error


def pull_container_image_from_registry(image_name):
    if image_name is None:
        raise Exception("The image name should not be null or empty")
    try:
        image = client.images.pull(image_name)
        if image is not None:
            pprint.pprint(f"Successfully pulled image from the registry. image={image_name}")
            return image
        else:
            raise Exception("Failed to pull image")
    except docker.errors.APIError as docker_error:
        pprint.pprint(f"Failed to pull image to the registry. error={docker_error}")
        raise docker_error


def push_container_image_to_registry(image_name_tmp, registry_address_tmp, registry_user_tmp, registry_password_tmp):
    if image_name_tmp is None:
        raise Exception("The image name should not be null or empty")
    if registry_address_tmp is None:
        raise Exception("The registry address should not be null or empty")
    if registry_user_tmp is None:
        raise Exception("The registry user should not be null or empty")
    if registry_password_tmp is None:
        raise Exception("The registry password should not be null or empty")

    try:
        # Login to registry
        login_to_registry(registry_address_tmp, registry_user_tmp, registry_password_tmp)
        # Push image to registry
        response = client.images.push(image_name_tmp)
        if response is not None:
            pprint.pprint(
                f"Successfully pushed image to registry. registry={registry_address_tmp}, image={image_name_tmp}, response={response}")
            return response
        else:
            raise Exception("Failed to push image.")
    except docker.errors.APIError as docker_error:
        pprint.pprint(f"Failed to push image to the registry. error={docker_error}")
        raise docker_error


def tag_image_for_registry(image_tmp, resource_id_tmp, resource_version_tmp, registry_address_tmp,
                           registry_repo_name_tmp):
    if image_tmp is None:
        raise Exception("The image should not be null or empty")
    if resource_id_tmp is None:
        raise Exception("The resource id should not be null or empty")
    if resource_version_tmp is None:
        raise Exception("The resource version should not be null or empty")
    if registry_address_tmp is None:
        raise Exception("The registry_address version should not be null or empty")
    if registry_repo_name_tmp is None:
        raise Exception("The registry_repo_name version should not be null or empty")

    try:
        # RESOURCE ID
        # pprint.pprint("Replacing '-' to '_' in resourceId")
        # resource_id_tmp = resource_id_tmp.replace("-", "_")

        if resource_version is not None:
            image_tag = f"{resource_id_tmp}:{resource_version}"
        else:
            image_tag = f"{resource_id_tmp}"

        complete_tag = f"{registry_address_tmp}/{registry_repo_name_tmp}/{image_tag}"
        # Tag image
        tagged = image_tmp.tag(complete_tag)
        if tagged is True:
            pprint.pprint(f"Successfully tagged image. image={image_tmp}, tag={complete_tag}")
            return complete_tag
        else:
            raise Exception("Failed to tag image.")
    except docker.errors.APIError as docker_error:
        pprint.pprint(f"Failed to tag image. error={docker_error}")
        raise docker_error


def pulling_tagging_pushing_image_to_registry(image_name_pull, resource_id_tag, resource_version_tag,
                                              registry_address_tmp, registry_repo_name_tmp, registry_user_tmp,
                                              registry_password_tmp):
    try:
        # Pull image
        image = pull_container_image_from_registry(image_name=image_name_pull)

        # Tag image
        tag_tmp = tag_image_for_registry(image_tmp=image, resource_id_tmp=resource_id_tag,
                                         resource_version_tmp=resource_version_tag,
                                         registry_address_tmp=registry_address_tmp,
                                         registry_repo_name_tmp=registry_repo_name_tmp)

        pprint.pprint("List available images: -->")
        pprint.pprint(client.images.list())

        # Push image
        push_container_image_to_registry(image_name_tmp=tag_tmp, registry_address_tmp=registry_address_tmp,
                                         registry_user_tmp=registry_user_tmp,
                                         registry_password_tmp=registry_password_tmp)

    except docker.errors.APIError as docker_error:
        pprint.pprint(f"Docker Exception occured!, exception={docker_error}")
    except Exception as ex:
        pprint.pprint(f"Exception occured!, exception={ex}")

################################
# SIMULATE EVENT FROM REGISTRY #
################################
def send_simulated_registry_event(resource_uuid_tmp):
    resource_url = f"{registry_address}/{registry_repo_name}/{resource_uuid_tmp}"
    json = {
            "type": "PUSH_ARTIFACT",
            "occur_at": 1626448868,
            "operator": "admin",
            "event_data": {
                "resources": [
                    {
                        "digest": "sha256:84075fa0ee8106f8e2975dca79d3c6f9587b41afefa7aec57e76a2fc9506df6c",
                        "tag": f"{resource_version}",
                        "resource_url": f"{resource_url}"
                    }
                ],
                "repository": {
                    "date_created": 1626448868,
                    "name": f"{resource_uuid_tmp}",
                    "namespace": f"{registry_repo_name}",
                    "repo_full_name": f"{registry_repo_name}/{resource_uuid_tmp}",
                    "repo_type": "private"
                }
            }
        }
    pprint.pprint("SENDING SIMULATED EVENT -->")
    pprint.pprint(json)
    url = f"{combined_host}/api/webhook/registry"
    with post_request_check_response(url, json=json, creds=True, ret_location=False) as response_tmp:
        pprint.pprint(f"Status_Code: {response_tmp.status_code} Response: {response_tmp.text}")
        response_tmp.raise_for_status()

#####################################################
# ARTIFACT REQUEST AND GET DATA METHODS #
#####################################################

def send_get_artifact(artifact_url_tmp):
    with get_request_check_response(artifact_url_tmp) as response_tmp:
        pprint.pprint(response_tmp.json())
    return response_tmp


def send_get_artifact_data(artifact_url_tmp, artifact_uuid_tmp):
    filename_tmp = f"{artifact_uuid_tmp}.json"
    url = artifact_url_tmp + "/data"
    with get_request_check_response(url) as response_tmp:
        pprint.pprint(response_tmp.status_code)
        pprint.pprint(response_tmp.json())
        with open(filename_tmp, 'wb') as f:
            f.write(response_tmp.content)
    return filename_tmp


####################
# CREATE RESOURCES #
####################
catalog = create_catalog()
resource = create_resource()
representation = create_representation()
dataApp = create_dataApp()
dataApp_endpoints = create_endpoints()
artifact = create_artifact()
contract = create_contract()
use_rule = create_rule_allow_access()

###########################
# CREATE RESOURCE LINKING #
###########################
add_resource_to_catalog(catalog, resource)
add_representation_to_resource(resource, representation)
add_app_to_representation(representation, dataApp)
add_endpoint_to_app(dataApp, dataApp_endpoints)
add_artifact_to_representation(representation, artifact)
add_contract_to_resource(resource, contract)
add_rule_to_contract(contract, use_rule)

############################
# GET RESOURCE DESCRIPTION #
############################
resource_uuid = resource.replace(f"{combined_host}/api/resources/", "")
artifact_uuid = artifact.replace(f"{combined_host}/api/artifacts/", "")
pprint.pprint(resource_uuid)

###########################
# SENDING SIMULATED EVENT #
###########################
##send_simulated_registry_event(resource_uuid)

##################################
# GET ARTIFACT AND ARTIFACT DATA #
##################################
##send_get_artifact(artifact)

##filename = send_get_artifact_data(artifact, artifact_uuid)
##pprint.pprint("File can be found in working directory: " + filename)

##########################
# DOCKER UPLOAD METHODS  #
##########################

pulling_tagging_pushing_image_to_registry(image_name_pull=image_name, resource_id_tag=resource_uuid,
                                          resource_version_tag=resource_id_tag_version,
                                          registry_address_tmp=registry_address,
                                          registry_repo_name_tmp=registry_repo_name,
                                          registry_user_tmp=registry_user,
                                          registry_password_tmp=registry_password)

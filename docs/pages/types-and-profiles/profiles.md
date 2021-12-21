---
layout: default
title: Profiles
nav_order: 1
permalink: /types-and-profiles/profiles
parent: Types and Profiles
---

# Data App - Profiles

There are currently four profiles for data apps which will be explained in the following section. The four profiles are divided into a basic and an advanced profile, each of which is further differentiated by whether the app has an integrated usage control implementation or not.

In general data apps do not need a standardized API. This just needs to be documented via the API specification in the metadata representation in a human- and machine-radable way.

The basic profile is assumed as the default value for all IDS data apps. Whether an app belongs to the advanced profile, on the other hand, is determined by whether one of the functionalities explained below is implemented in the app. Up to now, the implementation of usage control in a data app and/or the integration of communication with the Connetor interface have been the decisive criteria for classification in the advanced profile.

## Overview of Data App Profiles

| Type                                            | Apps                  | Certification      | 
|:----------------------------------------------- |:----------------------|:-------------------|
| [Basic Profile](#data-app)                      | data app, adapter app | App Criteria       | 
| [Basic Profile - Usage Control](#adapter-app)   | data app, adapter app | App Criteria       | 
| [Advanced Profile](#control-app)                | control app           | Connector Criteria | 
| [Advanced Profile - Usage Control](#app-bundle) | control app           | Connector Criteria | 

## <a name="basic"> Basic Profile </a>
_(Default Profile)_

In the Basic profile, an app must implement defined endpoints, depending on its app type. These endpoints must be described with the help of the IDS information model in terms of the protocols used and the data schemas.
Furthermore, a data app in the basic profile must ensure that it can be integrated into the data flow of a connector and can also work on this. In addition, an IDS Data App in the Basic profile must also be signed by the App developer before the App is loaded into the Appstore and distributed by it, in order to guarantee the origin of the Data App.

If an app implements an optional usage control endpoint that serves as a policy information point (PIP) and only provides general information about the app itself and its intended use, the app is still attributed to the **Basic Profile** due to its passive participation in the usage control process.

**Summary:**
- Defined endpoints depending on the app type (see: [Endpoints](../endpoints))
- Ability to integrate into the data flow of a connector and applied to it
- Data App must be signed by its developer before publishing and distributing to guarantee its origin
- Optional: Implementation of an usage control endpoint that acts as a policy information point (PIP)

## <a name="basic-uc"> Basic Profile - Usage Control </a>

If, in addition to the requirements already established from the base profile, active components for implementing usage policies are also implemented in the interaction between a data app and a usage control framework, the app is assigned to the base profile - usage control. In order for usage policies to be actively implemented at the app level, several policy enforcement points (PEP) must be implemented in addition to a policy information point (PIP) for general app and purpose information. The implementation of the policy enforcement points is tightly integrated in the app implementation. A usage control framework specific communnication is required at this point to be able to enforce several policies at the app implementation level. More information about usage control integration can be found here [Data App - Usage Control](../usage-control). 

**Summary:**
- Requirements from the **basic profile**
- Integration of policy information point (PIP) for general app and purpose information
- Integration of usage-control framework specific communication to enforce policies within an data app by implementing policy enforcement points (PEP)
- Active participation in the usage control implementation

## <a name="advanced"> Advanced Profile </a>

Data apps developed under the advanced profile are essentially subject to the conditions already listed for the basic profile. In addition, they are entitled to administrative access to the connector (core execution container) compared to the basic profile. This means that the Connector API is accessed directly from a data app at this point. This enables a data app to create data resources and contracts on a connector, among other things. The administrative access to a connector should be considered and handled in a special way in any case due to its power. Data apps with this profile are also subject to the connector criteria for certification due to the administrative access.

**Summary:**
- Requirements from the **basic profile**
- Adminstrative access towards a connector API (core execution container) 

In the Advanced profile, additional requirements are added to those already set out in the Basic profile. The profiles differ particularly with regard to the active integration of usage control. If a data app integrates an active part in the implementation of usage control, it is automatically assigned to the advanced profile. Further information on the integration of Policy Enforcement Points (PEP) in an app can be found in the [usage control document](../usage-control). 

## <a name="advanced-uc"> Advanced Profile - Usage Control </a>

If, in addition to the requirements already established from the advanced profile, active components for implementing usage policies are also implemented in the interaction between a data app and a usage control framework, the app is assigned to the advanced profile - usage control. In order for usage policies to be actively implemented at the app level, several policy enforcement points (PEP) must be implemented in addition to a policy information point (PIP) for general app and purpose information. The implementation of the policy enforcement points is tightly integrated in the app implementation. A usage control framework specific communnication is required at this point to be able to enforce several policies at the app implementation level. For more informatione about usage control see [Usage Control](../usage-control).

**Summary:**
- Requirements from the **advanced profile**
- Integration of policy information point (PIP) for general app and purpose information
- Integration of usage-control framework specific communication to enforce policies within an data app by implementing policy enforcement points (PEP)
- Active participation in the usage control implementation
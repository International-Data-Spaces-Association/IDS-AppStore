---
layout: default
title: App Development
nav_order: 1
permalink: /data-app/app-development
parent: Data App
---

# App Development
{: .fs-9 }

**This section is currently being edited.** In the future, this section will include a data app development guide.
{: .fs-6 .fw-300 }

---

An app in the IDS ecosystem mainly consists of two components. First, an application needs to be provided as a **Docker container**. Second, the **metadata description** of the app according to the IDS Information Model. The application metadata is used later for the application deployment inside of the IDS Connector and for the interaction with the IDS App Store. The app itself can provide some [endpoints](https://international-data-spaces-association.github.io/IDS-AppStore/endpoints) that are described in the metadata description and required for consuming, providing, and processing data. 

## IDS App Template

A template of an IDS App can be found at the following repository: <https://gitlab.cc-asp.fraunhofer.de/fhg-fit-ids/ids-app-template>
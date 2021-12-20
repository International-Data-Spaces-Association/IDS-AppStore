---
layout: default
title: Getting Started
nav_order: 3
description: ""
permalink: /getting-started
---

# Getting Started
{: .fs-9 }

<p>
  <a href="mailto:info@dataspace-connector.de">Contact</a> •
  <a href="#contributing">Contribute</a> •
  <a href="https://international-data-spaces-association.github.io/IDS-AppStore/">Docs</a> •
  <a href="https://github.com/International-Data-Spaces-Association/IDS-AppStore/issues">Issues</a> •
  <a href="#license">License</a>
</p>

Get an example setup running without diving into the code.
{: .fs-6 .fw-300 }

---

## Quick Start

If you want to build and run locally, ensure that at least Java 11 is installed. Then, follow these steps:

1.  Clone this repository: `git clone https://github.com/International-Data-Spaces-Association/IDS-AppStore.git`
2.  Execute `cd IDS-AppStore` and `./mvnw clean package -DskipTests`.
3.  Navigate to `/target` and run `java -jar ids-appstore-{VERSION}.jar`.
4.  If everything worked fine, the application is available at https://localhost:8080/. The API can
    be accessed at https://localhost:8080/api. The Swagger UI can be found at https://localhost:8080/api/docs.

## Contributing

You are very welcome to contribute to this project when you find a bug, want to suggest an
improvement, or have an idea for a useful feature. Please find a set of guidelines at the
[CONTRIBUTING.md](CONTRIBUTING.md) and the [CODE_OF_CONDUCT.md](CODE_OF_CONDUCT.md).

## Developers

This is an ongoing project of the [Fraunhofer FIT](https://www.fit.fraunhofer.de/en.html), based on
the open source project [Dataspace Connector](https://github.com/International-Data-Spaces-Association/DataspaceConnector) - starting at version 6.1.0.
The core development is driven by
* [Christoph Quix](https://www.fit.fraunhofer.de/de/geschaeftsfelder/data-science-und-kuenstliche-intelligenz/datenmanagement.html), [Fraunhofer FIT](https://www.fit.fraunhofer.de/en.html), project owner
* [Ahmad Hemid](mailto:ahmad.hemid@fit.fraunhofer.de), [Fraunhofer FIT](https://www.fit.fraunhofer.de/en.html), lead developer

Previously, the core development was driven by:
* [Dominic Reuter](https://github.com/DominicReuter-FIT), [Fraunhofer FIT](https://www.fit.fraunhofer.de/en.html)

## License
Copyright © 2021 Fraunhofer FIT. This project is licensed under the Apache License 2.0 - see the
[LICENSE](LICENSE) for details.

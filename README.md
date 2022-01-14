<h1 align="center">
  <br>
      IDS AppStore
  <br>
</h1>


<p align="center">
  <a href="mailto:info@dataspace-connector.de">Contact</a> •
  <a href="#contributing">Contribute</a> •
  <a href="https://international-data-spaces-association.github.io/IDS-AppStore/">Docs</a> •
  <a href="https://github.com/International-Data-Spaces-Association/IDS-AppStore/issues">Issues</a> •
  <a href="#license">License</a>
</p>


...


## Quick Start

If you want to build and run locally, ensure that at least Java 11 is installed. Then, follow these steps:

1.  Clone this repository.
2.  Create the Data folder on the root `cd /` and `mkdir data`
3.  Execute `cd IDS-AppStore`, `chmod +x mvnw` and `./mvnw clean package -DskipTests -Dmaven.javadoc.skip=true`.
5.  Navigate to `/target` and run `java -jar ids-appstore-{VERSION}.jar`.
6.  If everything worked fine, the application is available at https://localhost:8080/. The API can
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

with significant contributions, comments, and support by (in alphabetical order):
* ...

## License
Copyright © 2021 Fraunhofer FIT. This project is licensed under the Apache License 2.0 - see the
[LICENSE](LICENSE) for details.

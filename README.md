# KIT Data Manager - Metastore Service

This project contains metastore service microservice used by the KIT DM infrastructure. The service provides
metadata management, e.g. register xsd files, validate xml files, store/access/update metadata.

## Installation Research Data Repository including Metastore

## Prerequisites

In order to build this microservice you'll need:

* Java SE Development Kit 8 or higher

## Installing Research Data Repository
There are two options for installing:
For internal use without any authorization for writing/accessing data see: [Installation OCR-D Research Data Repository using Docker Compose](installDocker/installation.md)
For external use with authorization for ingest but public access for data please refer to ['Installation KIT Data Manager - Metastore Service'](installation.md) 
To build and install the microservice, please follow the installation manual:
[Installation KIT Data Manager - Metastore Service](installation.md)


## How to start
As soon as the microservice is started, you can browse to 

http://localhost:8080/swagger-ui.html

in order to see available RESTful endpoints and their documentation. Furthermore, you can use a simple web interface (http://localhost:8080/api/v1/metastore/bagit) to test the repository and get familiar with the service.


## More Information

* [KIT DM 2.0](https://github.com/kit-data-manager/base-repo.git)
* [Docker](https://www.docker.com/)
* [ArangoDB](https://www.arangodb.com/)
* [ArangoDB(Docker)](https://hub.docker.com/r/arangodb/arangodb/)
* [Spring Boot Security](https://docs.spring.io/spring-boot/docs/2.0.5.RELEASE/reference/htmlsingle/#boot-features-security)

## License

The Metastore service is licensed under the Apache License, Version 2.0.
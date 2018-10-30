# KIT Data Manager - Metastore Service

This project contains metastore service microservice used by the KIT DM infrastructure. The service provides
metadata management, e.g. register xsd files, validate xml files, store/access/update metadata.

## Requirements
In order to run the microservice an ArangoDB server is needed. Alternatively a docker image
could be used.

## How to build

In order to build this microservice you'll need:

* Java SE Development Kit 8 or higher

After obtaining the sources change to the folder where the sources are located perform the following steps:

```
user@localhost:/home/user/metastore-service$./gradlew build
BUILD SUCCESSFUL in 3s
6 actionable tasks: 6 executed
user@localhost:/home/user/metastore-service$
```

As a result, a fat jar containing the entire service is created at 'build/libs/metastore-service-0.1.0.jar'.


## How to start

Before you are able to start the metastore service microservice, you have to modify the application properties according to your local setup. 
Therefor, copy the file 'settings/application.properties' to your project folder and customize it. Special attention should be payed to the
properties in the 'arangodb' section. 


As soon as 'application.properties' is completed, you may start the repository microservice by executing the following command inside the project folder, 
e.g. where the service has been built before:

```
user@localhost:/home/user/base-repo$ 
user@localhost:/home/user/base-repo$ java -jar build/libs/metastore-service-0.1.0.jar

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.0.3.RELEASE)
[...]
1970-01-01 00:00:00.000  INFO 56918 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
[...]
```

If your 'application.properties' is not located inside the project folder you can provide it using the command line argument --spring.config.location=<PATH_TO_APPLICATION.YML>
As soon as the microservice is started, you can browse to 

http://localhost:8080/swagger-ui.html

in order to see available RESTful endpoints and their documentation. Furthermore, you can use this Web interface to test single API calls in order to get familiar with the 
service.

### Quick start using docker (Only for tests!)
1. Start ArangoDB using docker

```
user@localhost:/home/user/$docker run -p 8578:8529 -e ARANGO_ROOT_PASSWORD=ocrd-testOnly arangodb/arangodb
[...]
2018-10-30T07:55:29Z [1] INFO ArangoDB (version 3.3.19 [linux]) is ready for business. Have fun!
```
2. Start metastore service microservice

```
user@localhost:/home/user/base-repo$ 
user@localhost:/home/user/base-repo$ java -jar build/libs/metastore-service-0.1.0.jar

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.0.3.RELEASE)
[...]
1970-01-01 00:00:00.000  INFO 56918 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
[...]
```
## First steps
1. Upload zipped BagIt container to metastore.
```
curl -F "file=@zippedBagItContainer" http://localhost:8080/metastore/bagit/ 
```
2. List all METS files.
```
curl -XGET http://localhost:8080/metastore/mets
```
3. List all METS files with title 'Der Herold'.
```
curl -H "Accept:application/json" "http://localhost:8080/metastore/mets/title?title=Der Herold"
```
4. Download zipped BagIt container to metastore.
```
curl -XGET http://localhost:8080/metastore/bagit/files/zippedBagItContainer > bagDownload.zip
```

## More Information

* [Docker](https://www.docker.com/)
* [ArangoDB](https://www.arangodb.com/)
* [ArangoDB(Docker)](https://hub.docker.com/r/arangodb/arangodb/)

## License

The Metastore service is licensed under the Apache License, Version 2.0.
[![Build Status](https://travis-ci.com/VolkerHartmann/repository_metastore.svg?branch=master)](https://travis-ci.com/VolkerHartmann/repository_metastore)
[![codecov](https://codecov.io/gh/VolkerHartmann/repository_metastore/branch/master/graph/badge.svg)](https://codecov.io/gh/VolkerHartmann/repository_metastore)
# KIT Data Manager - Metastore Service

This project contains metastore service microservice used by the KIT DM infrastructure. The service provides
metadata management, e.g. register xsd files, validate xml files, store/access/update metadata.

## Prerequisites
- [KIT DM 2.0](https://github.com/kit-data-manager/base-repo.git) 
- PostGresSQL 9.1 or higher
- ArangoDB 3.3 or higher

## How to build

In order to build this microservice you'll need:

* Java SE Development Kit 8 or higher

To build and install the microservice, please follow the installation manual:
[Installation KIT Data Manager - Metastore Service](installation.md)

## How to start

Before you are able to start the metastore service microservice, you have to modify the application properties according to your local setup. 
Therefor, copy the file 'settings/application.properties' to your project folder and customize it. Special attention should be payed to the
properties in the 'arangodb' section. 


As soon as 'application.properties' is completed, you may start the repository microservice by executing the following command inside the project folder, 
e.g. where the service has been built before:

```bash=bash
user@localhost:/home/user/repository_metastore/$ java -jar build/libs/metastore-service-0.1.0.jar

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

in order to see available RESTful endpoints and their documentation. Furthermore, you can use this Web interface to test single API calls in order to get familiar with the service.

### Quick start using docker (Only for tests!)
#### Install KIT DM 2.0 
```bash=bash
# Build KIT DM 2.0
user@localhost:/home/user/$git clone https://github.com/kit-data-manager/base-repo.git
user@localhost:/home/user/$cd base-repo
user@localhost:/home/user/base-repo/$git submodule init
user@localhost:/home/user/base-repo/$git submodule update --remote --merge 
user@localhost:/home/user/base-repo/$cd libraries/service-base/
user@localhost:/home/user/base-repo/libraries/service-base/$gradlew install
user@localhost:/home/user/base-repo/libraries/service-base/$cd ../../
user@localhost:/home/user/base-repo/$cd libraries/generic-message-consumer/
user@localhost:/home/user/base-repo/libraries/generic-message-consumer/$gradlew install
user@localhost:/home/user/base-repo/libraries/generic-message-consumer/$cd ../../
user@localhost:/home/user/base-repo/$gradlew build
# Configure KIT DM 2.0
user@localhost:/home/user/base-repo/$cp conf/application.yml .
```
##### Edit application.yml.
a) Configure connection to database:
```
spring:
    datasource:
       url: jdbc:postgresql://localhost:5555/postgres
       username: postgres
       password: postgres
       driverClassName: org.postgresql.Driver
    jpa:
      database: POSTGRESQL
      database-platform: org.hibernate.dialect.PostgreSQLDialect
    hibernate:
      ddl-auto: update
      properties:
        #hibernate:
          #show_sql: true
          #use_sql_comments: true
          #format_sql: true
    ###########################################################################
    ###                  MAX file size for up-/download                     ###
    ###########################################################################
    servlet:
      # Max file size.   
      multipart.max-file-size: 10MB
      # Max request size.
      multipart.max-request-size: 10MB
```      
b) Change basepath for archive, disable authentication and messaging
```
repo:
   auth:
      jwtSecret: test123
      enabled: false
   basepath:  file:///tmp/kitdm2.0/ 
   messaging:
      enabled: false 
```
##### Build and start docker container with postgreSQL
First time you have to build docker container:
```bash=bash
user@localhost:/home/user/$docker run -p 5555:5432 --name postgres4kitdm -e POSTGRES_PASSWORD=postgres -d postgres
```
To start/stop docker container afterwards use
```bash=bash
user@localhost:/home/user/$docker stop postgres4kitdm
user@localhost:/home/user/$docker start postgres4kitdm
```
##### Start KIT DM 2.0
After starting the database, the repository can be started.
```bash=bash
user@localhost:/home/user/base-repo/$java -jar build/libs/base-repo.jar 
```
#### Start ArangoDB using docker

```bash=bash
user@localhost:/home/user/$docker run -p 8578:8529 -e ARANGO_ROOT_PASSWORD=ocrd-testOnly arangodb/arangodb
[...]
2018-10-30T07:55:29Z [1] INFO ArangoDB (version 3.3.19 [linux]) is ready for business. Have fun!
```
#### Start metastore service microservice

```bash=
user@localhost:/home/user/metastore-service$ java -jar build/libs/metastore-service-0.1.0.jar

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
Using generated security password: GENERATED_PASSWORD
[...]
```
## First steps
The write access to the service is secured with a password, which is regenerated every time the service is started. There is an 'ingest' user for ingesting files.
1. Upload zipped BagIt container to metastore.
```bash=bash
user@localhost:/home/user/$curl -u ingest:GENERATED_PASSWORD -v -F "file=@zippedBagItContainer" http://localhost:8080/api/v1/metastore/bagit 
```
2. List all BagIt containers.
```bash=bash
user@localhost:/home/user/$curl -XGET -H "Accept:application/json"  http://localhost:8080/api/v1/metastore/bagit 
```
3. List all METS files.
```bash=bash
user@localhost:/home/user/$curl -XGET http://localhost:8080/api/v1/metastore/mets
```
4. List all METS files with title 'Der Herold'.
```bash=bash
user@localhost:/home/user/$curl -XGET -H "Accept:application/json" "http://localhost:8080/api/v1/metastore/mets/title?title=Der%20Herold"
```
5. Download zipped BagIt container to metastore.
```bash=bash
user@localhost:/home/user/$curl -XGET http://localhost:8080/api/v1/metastore/bagit/files/zippedBagItContainer > bagDownload.zip
```
You may also try these URLs in a browser. (http://localhost:8080/api/v1/metastore/bagit)


## More Information

* [KIT DM 2.0](https://github.com/kit-data-manager/base-repo.git)
* [Docker](https://www.docker.com/)
* [ArangoDB](https://www.arangodb.com/)
* [ArangoDB(Docker)](https://hub.docker.com/r/arangodb/arangodb/)
* [Spring Boot Security](https://docs.spring.io/spring-boot/docs/2.0.5.RELEASE/reference/htmlsingle/#boot-features-security)

## License

The Metastore service is licensed under the Apache License, Version 2.0.
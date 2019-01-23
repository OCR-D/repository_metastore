# Installation OCR-D Research Data Repository

## Prerequisites

In order to build this microservice you'll need:

* Java SE Development Kit 8 or higher (openJDK) 
* Docker
* Docker Compose

## Install OCR-D Research Data Repository 
:information_source: This repository is only for internal usage. There is no support for authentication and/or authorization.
All data inside the repository is available via the REST interface. If authorization is an issue please refer to
the [../installation.md](detailed installation). 

## Install ArangoDB  
If ArangoDB is not already installed you also have to [install ArangoDB](installation_arangodb.md). 

## Install Metastore Service 
```bash=bash
# Build metastore service
user@localhost:/home/user/$git clone https://github.com/OCR-D/repository_metastore.git
Cloning into 'repository_metastore'...
[...]
Resolving deltas: 100% (451/451), done.
user@localhost:/home/user/$cd repository_metastore
user@localhost:/home/user/repository_metastore/$./gradlew build
BUILD SUCCESSFUL in 5s
7 actionable tasks: 7 executed
```

As a result, a fat jar containing the entire service is created at 'build/libs/metastore-service-0.1.0.jar'.

### Configure Metastore Service
```bash=bash
user@localhost:/home/user/repository_metastore/$cp conf/application.properties .
```
#### Edit application.properties
##### Configure KIT Data Manager 2.0
```
################################################################################
###                    Configuration for Security                            ###
################################################################################
[...]
# password should be a random string. 
# Please generate it with a tool: e.g.: 'uuidgen' or 'pwgen 36 1'
spring.security.user.password=e.g.:eeedfaf4-61ba-4a7a-8d4e-8b2a3342d4a1
[...]
```      

##### Configure KIT Data Manager 2.0
```
################################################################################
###                    Configuration for KITDM 2.0                           ###
################################################################################
[...]
kitdm20.authentication=false   (set this according to settings at KIT_DM2.0)
[...]
kitdm20.password=PASSWORD_OCRD_USER
[...]
```      

##### Configure ArangoDB
```
################################################################################
###                    Configuration for ArangoDB                            ###
################################################################################
[...]
arangodb.password=GENERATED_ROOT_PASSWORD 
[...]
```      

##### Configure Logging
```
################################################################################
###                    Configuration for Logging                             ###
################################################################################
[...]
logging.level.root=INFO
#logging.level.edu.kit.datamanager=TRACE
[...]
```      

##### Configure Max File Size for Up-/Download
```
################################################################################
###                    MAX file size for up-/download                        ###
################################################################################
# Max file size.
spring.servlet.multipart.max-file-size=1000MB
# Max request size.
spring.servlet.multipart.max-request-size=1000MB
```      

## Start metastore service microservice
The following services should be available and running:
* PostgreSQL
* ArangoDB
* KIT Data Manager 2.0 
* Authentication Service (optional)

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
user@localhost:/home/user/$curl -u ingest:ingest -v -F "file=@zippedBagItContainer" http://localhost:8080/api/v1/metastore/bagit 
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
5. Download zipped BagIt container from metastore. (use one URL of the list printed above) 
```bash=bash
user@localhost:/home/user/$curl -XGET http://localhost:8090/api/v1/dataresources/123..../data/zippedBagItContainer > bagDownload.zip
```
You may also try these URLs in a browser. (http://localhost:8080/api/v1/metastore/bagit)


## Installing Spring Boot Applications
To start all services during startup, please have a look at this site: 
[Installing Spring Boot Applications](https://docs.spring.io/spring-boot/docs/current/reference/html/deployment-install.html)
If there is no root access you may start the job via cronjob:
[Execute Cron Job After System Reboot](https://www.cyberciti.biz/faq/linux-execute-cron-job-after-system-reboot/)

## More Information

* [Docker](https://www.docker.com/)
* [Docker Compose](https://docs.docker.com/compose/)

## License

The Metastore service is licensed under the Apache License, Version 2.0.

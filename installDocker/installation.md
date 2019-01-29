# Installation OCR-D Research Data Repository

## Prerequisites

In order to build this microservice you'll need:

* Java SE Development Kit 8 or higher (openJDK) 
* Docker ([Installation Docker](installationDocker.md))
* Docker Compose ([Installation Docker Compose](installationDocker.md#installation-docker-compose))


## Install OCR-D Research Data Repository 
:information_source: This repository is only for internal usage. There is no support for authentication and/or authorization.
All data inside the repository is available via the REST interface. If authorization is an issue please refer to
the [detailed installation](../installation.md). 

To install the OCR-D Research Data Repository you have to execute the shell script 'installRepo.sh' providing a
(new) directory for the repository and its data.

```bash=bash
# Build OCR-D Research Data Repository
user@localhost:/home/user/repository_metastore/installDocker/$bash installRepo.sh /ocrd/repository/
[...]
SUCCESS
Now you can start the Research Data Repository with the following commands:
su
docker-compose up
user@localhost:/ocrd/repository/docker/$bash installRepo.sh
```
Now the Research Data Repository is ready to start. Switch to super user and start 
the docker containers.
```bash=bash
# Build OCR-D Research Data Repository
user@localhost:/home/user/repository_metastore/installDocker/$bash installRepo.sh /ocrd/repository/
[...]
SUCCESS
Now you can start the Research Data Repository with the following commands:
su
docker-compose up
user@localhost:/ocrd/repository/docker/$
```
## Start Research Data Repository
From your installation directory, start up your application by running 'docker-compose up' (as root).
```bash=bash
# Start OCR-D Research Data Repository
user@localhost:/ocrd/repository/docker/$su
user@localhost:/ocrd/repository/docker/$docker-compose up
```
## Stop Research Data Repository
Stop the application, either by running 'docker-compose down' from within your project directory 
in the second terminal, or by hitting CTRL+C in the original terminal where you started the app.
```bash=bash
# Start OCR-D Research Data Repository
user@localhost:/ocrd/repository/docker/$sudo su
user@localhost:/ocrd/repository/docker/$docker-compose stop
```
## Backup PostgreSQL and Arangodb
To backup the databases you have to use the following commands:
```bash=bash
# Backup postgreSQL
user@localhost:/home/user/$sudo su
user@localhost:/home/user/$docker exec -ti docker_postgres4kitdm_1 sh -c "pg_dump -U postgres -h 127.0.0.1 kitdm20 > /dump/database_dump_kitdm20_`date +%Y_%m_%dt%H_%M`.sql"
```
```bash=bash
# Backup arangodb
user@localhost:/home/user/$sudo su
user@localhost:/home/user/$docker exec -ti docker_arangodb4kitdm_1 sh -c "arangodump --include-system-collections true --server.database metastore-OCR-D --output-directory /dump/dump_`date +%Y_%m_%dt%H_%M`"
Please specify a password: arangodb4Docker
[...]
Wrote xxxx byte(s) into datafiles, sent 9 batch(es)
```

## First Steps
For first steps [see First Steps](../installation.md#first-steps).
Please use 
*   username: ingest
*   password: ingest

for uploading zipped bagit container.

## More Information

* [Docker](https://www.docker.com/)
* [Docker Compose](https://docs.docker.com/compose/)
* [ArangoDB - Manual](https://docs.arangodb.com/3.4/Manual/)


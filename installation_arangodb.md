# Installation ArangoDB

## Prerequisites
- Docker version 18.06 or higher

## Build and start docker container with ArangoDB
First time you have to build docker container:
```bash=bash
# Create directory for database
user@localhost:/home/user/$mkdir -p server/arangodb
# Create directory for database dumps
user@localhost:/home/user/$mkdir -p server/backup/arangodb
user@localhost:/home/user/$docker run -e ARANGO_RANDOM_ROOT_PASSWORD=1 -p 8539:8529 --name arangodb4kitdm -d -v /home/user/server/arangodb:/var/lib/arangodb3 -v /home/user/server/backup/arangodb:/dump arangodb
123.....
user@localhost:/home/user/$docker logs arangodb4kitdm |head -3
===========================================
GENERATED ROOT PASSWORD: GENERATED_ROOT_PASSWORD
===========================================
user@localhost:/home/user/$
```
To start/stop docker container afterwards use
```bash=bash
user@localhost:/home/user/$docker stop arangodb4kitdm
user@localhost:/home/user/$docker start arangodb4kitdm
```
### Backup ArangoDB
```bash=bash
# Backup ArangoDB
ArangoDBuser@localhost:/home/user/$docker exec -ti arangodb4kitdm sh -c "arangodump --include-system-collections true --server.database metastore-OCR-D --output-directory /dump/dump_`date +%Y_%m_%dt%H_%M`"
```

## More Information

* [Docker](https://www.docker.com/)
* [ArangoDB - Manual](https://docs.arangodb.com/3.4/Manual/)


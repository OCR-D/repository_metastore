# Installation PostgreSQL

## Prerequisites
- Docker version 18.06 or higher

## Build and start docker container with postgreSQL
First time you have to build docker container. Port 5555 is used to avoid overlap.
```bash=bash
# Create directory for database dumps
user@localhost:/home/user/$mkdir -p server/backup/postgres
user@localhost:/home/user/$docker run -p 5555:5432 --name postgres4kitdm -e POSTGRES_PASSWORD=YOUR_ADMIN_PASSWORD -d  -v /home/user/server/backup/postgres:/dump postgres
123.....
```
Now postgreSQL is available on localhost via port 5555.

## Setup Database
```bash=bash
user@localhost:/home/user/$docker exec -ti postgres4kitdm sh -c "psql postgres -h localhost -d postgres"
psql (11.0 (Debian 11.0-1.pgdg90+2))
Type "help" for help.

postgres=# CREATE DATABASE kitdm20;
CREATE DATABASE
postgres=# CREATE DATABASE kitdm20_auth;
CREATE DATABASE
postgres=# CREATE USER kitdm_admin WITH ENCRYPTED PASSWORD 'KITDM_ADMIN_PASSWORD';
CREATE ROLE
postgres=# GRANT ALL PRIVILEGES ON DATABASE kitdm20 TO kitdm_admin;
GRANT
postgres=# GRANT ALL PRIVILEGES ON DATABASE kitdm20_auth TO kitdm_admin;
GRANT
postgres=# \q
user@localhost:/home/user/$
```
Now postgreSQL is setup for KIT DM 2.0 and Authentication.

To start/stop docker container afterwards use
```bash=bash
user@localhost:/home/user/$docker stop postgres4kitdm
user@localhost:/home/user/$docker start postgres4kitdm
```
## Backup PostgreSQL
```bash=bash
# Backup kitdm20
user@localhost:/home/user/$docker exec -ti postgres4kitdm sh -c "pg_dump -U postgres -h 127.0.0.1 kitdm20 > /dump/database_dump_kitdm20_`date +%Y_%m_%dt%H_%M`.sql"
# Backup kitdm20 Authentication
user@localhost:/home/user/$docker exec -ti postgres4kitdm sh -c "pg_dump -U postgres -h 127.0.0.1 kitdm20_auth > /dump/database_dump_kitdm20_auth_`date +%Y_%m_%dt%H_%M`.sql"
```


## More Information

* [Docker](https://www.docker.com/)
* [PostgreSQL - Add user to database](https://medium.com/coding-blocks/creating-user-database-and-adding-access-on-postgresql-8bfcd2f4a91e)


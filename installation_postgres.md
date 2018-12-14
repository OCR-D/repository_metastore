# Installation Postgres

## Prerequisites
- Docker version 18.06 or higher

##### Build and start docker container with postGreSQL
First time you have to build docker container:
```bash=bash
user@localhost:/home/user/$docker run -p 5555:5432 --name postgres4kitdm -e POSTGRES_PASSWORD=YOUR_ADMIN_PASSWORD -d postgres
123.....
user@localhost:/home/user/$psql postgres -p 5555 -h localhost -d postgres
Password for user postgres: (YOUR_ADMIN_PASSWORD)
psql (9.6.10, server 11.0 (Debian 11.0-1.pgdg90+2))
WARNING: psql major version 9.6, server major version 11.
         Some psql features might not work.
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
To start/stop docker container afterwards use
```bash=bash
user@localhost:/home/user/$docker stop postgres4kitdm
user@localhost:/home/user/$docker start postgres4kitdm
```


## More Information

* [Docker](https://www.docker.com/)
* [PostgreSQL - Add user to database](https://medium.com/coding-blocks/creating-user-database-and-adding-access-on-postgresql-8bfcd2f4a91e)


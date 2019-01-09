# Installation and Setup of Authentication Service

## Prerequisites

In order to build this microservice you'll need:

* Java SE Development Kit 8 or higher
* PostgreSQL
* curl

## What do you need?
* Connection string to database (e.g.: jdbc:postgresql://localhost:5555/kitdm20_auth)
* username for kitdm2.0_auth
* password for kitdm2.0_auth

## Install KIT DM 2.0 Authentication Service
```bash=bash
# Build KIT DM 2.0 Authentication Service
user@localhost:/home/user/$git clone https://github.com/kit-data-manager/auth-service.git
Cloning into 'auth-service'...
[...]
Resolving deltas: 100% (380/380), done.
user@localhost:/home/user/$cd auth-service
user@localhost:/home/user/auth_service/$git submodule init
Submodule 'libraries/service-base' (git://github.com/kit-data-manager/service-base.git) registered for path 'libraries/service-base'
user@localhost:/home/user/auth_service/$git submodule update --remote --merge 
Cloning into '/home/user/auth-service/libraries/service-base'...
Submodule path 'libraries/service-base': checked out '0c...'
user@localhost:/home/user/auth_service/$cd libraries/service-base/
user@localhost:/home/user/auth_service/libraries/service-base/$./gradlew install
[...]
BUILD SUCCESSFUL in 5s
3 actionable tasks: 3 executed
user@localhost:/home/user/auth_service/libraries/service-base/$cd ../../
user@localhost:/home/user/auth_service/$./gradlew build
[...]
BUILD SUCCESSFUL in 45s
5 actionable tasks: 5 executed
```
### Configure KIT DM 2.0 Authentication Service
```bash=bash
user@localhost:/home/user/auth_service/$cp conf/application.properties .
```
#### Edit application.properties
##### Configure port of auth service, connection to database and jwtSecret:
```
#server settings
server.port: 8070

[...]
spring.datasource.url: jdbc:postgresql://localhost:5555/kitdm20_auth
spring.datasource.username: kitdm_admin
spring.datasource.password: KITDM_ADMIN_PASSWORD
[...]
#kit dm settings
repo.auth.jwtSecret:ANY_JWT_SECRET

```      
:information_source: jwtSecret has to be the same as the one used in KIT DM 2.0.
:information_source: Please avoid any whitespaces in front or behind 'ANY_JWT_SECRET'

## Start authentication service
```bash=bash
user@localhost:/home/user/auth_service/$java -jar build/libs/auth-service.jar

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.0.0.RELEASE)
[...]
Spring is running!
```
### Setup Users and Groups
### Add admin user
Please use a new terminal.
```bash=bash
user@localhost:/home/user/$curl -v -X POST 'http://localhost:8070/api/v1/users/' -H 'accept: */*' -H 'Content-Type: application/json' -d '{ "email": "YOUR_EMAIL_ADDRESS",  "password": "YOUR_ADMIN_PASSWORD",  "username": "admin"}'
{"id":1,"username":"admin" [...]
user@localhost:/home/user/$
```
### Add user 'ocrd' and create group 'OCRD' 
```bash=bash
# Add user for OCR-D
user@localhost:/home/user/$curl -X POST 'http://localhost:8070/api/v1/users/' -H 'accept: */*' -H 'Content-Type: application/json' -d '{ "email": "YOUR_EMAIL_ADDRESS",  "password": "PASSWORD_OCRD_USER",  "username": "ocrd"}'
{"id":2,"username":"ocrd"
# Get bearer token for subsequent calls
user@localhost:/home/user/$curl  -u ocrd:PASSWORD_OCRD_USER -X POST 'http://localhost:8070/api/v1/login' -H  'accept: */*'
ey[...]
# Create group 'OCRD' for OCR-D and add ocrd user
user@localhost:/home/user/$curl -X POST 'http://localhost:8070/api/v1/groups/' -H 'Authorization: Bearer ey[...]' -H  'accept: */*' -H  'Content-Type: application/json' -d '{  "groupname": "OCRD"}'
[{"id":2,"groupname":"OCRD" [...]
```

## More Information

* [Authentication Service](https://github.com/kit-data-manager/auth-service)


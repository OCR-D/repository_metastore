# Installation KIT Data Manager

## Prerequisites

In order to build this microservice you'll need:

* Java SE Development Kit 8 or higher
* PostgreSQL
* KITDM 2.0 Authentication Service (optional)

## Install PostgreSQL 
[Install PostgreSQL with docker](installation_postgres.md)

## Install KIT DM 2.0 
```bash=bash
# Build KIT DM 2.0
# Create archive folder for KIT DM 2.0
user@localhost:/home/user/$mkdir -p server/kitdm2.0/archive
user@localhost:/home/user/$git clone https://github.com/kit-data-manager/base-repo.git
Cloning into 'base-repo'...
[...]
Resolving deltas: 100% (357/357), done.
user@localhost:/home/user/$cd base-repo
user@localhost:/home/user/base-repo/$git submodule init
Submodule 'libraries/service-base' (git://github.com/kit-data-manager/service-base.git) registered for path 'libraries/service-base'
Submodule 'libraries/generic-message-consumer' (git://github.com/kit-data-manager/generic-message-consumer.git) registered for path 'libraries/generic-message-consumer'
user@localhost:/home/user/base-repo/$git submodule update --remote --merge 
Cloning into '/home/user/base-repo/libraries/service-base'...
Submodule path 'libraries/service-base': checked out 'a9...'
Cloning into '/home/user/base-repo/libraries/generic-message-consumer'...
Submodule path 'libraries/generic-message-consumer': checked out '98...'
user@localhost:/home/user/base-repo/$cd libraries/service-base/
user@localhost:/home/user/base-repo/libraries/service-base/$./gradlew install
Starting a Gradle Daemon (subsequent builds will be faster)
[...]
BUILD SUCCESSFUL in 10s
3 actionable tasks: 3 executed
user@localhost:/home/user/base-repo/libraries/service-base/$cd ../../
user@localhost:/home/user/base-repo/$cd libraries/generic-message-consumer/
user@localhost:/home/user/base-repo/libraries/generic-message-consumer/$gradlew install
Starting a Gradle Daemon
[...]
BUILD SUCCESSFUL in 5s
3 actionable tasks: 3 executed
user@localhost:/home/user/base-repo/libraries/generic-message-consumer/$cd ../../
user@localhost:/home/user/base-repo/$./gradlew build
[...]
BUILD SUCCESSFUL in 51s
7 actionable tasks: 7 executed
```
### Configure KIT DM 2.0
```bash=bash
user@localhost:/home/user/base-repo/$cp conf/application.properties .
```
#### Edit application.properties
##### Configure port, max file size,connection to database and authentication:
```
#server settings
server.port: 8090
[...]
spring.servlet.multipart.max-file-size: 1000MB
spring.servlet.multipart.max-request-size: 1000MB

#spring datasource settings
spring.datasource.platform: postgres
spring.datasource.url: jdbc:postgresql://localhost:5555/kitdm20
spring.datasource.username: kitdm_admin
spring.datasource.password: KITDM_ADMIN_PASSWORD
[...]
```      
##### Change basepath for archive, disable or enable authentication, set jwtSecret.
'jwtSecret' should be a random string. (Please generate it with a tool: e.g.: 'uuidgen' or 'pwgen -y 36 1') 
'jwtSecret' is only needed when authentication is enabled.
```
[...]
#kit dm settings
repo.auth.enabled: false (in case of authentication set this value to true)
repo.auth.jwtSecret:ANY_JWT_SECRET
repo.basepath:  file:///home/user/server/kitdm2.0/archive
repo.messaging.enabled: false
[...]
```
:information_source: Please avoid any whitespaces in front or behind 'ANY_JWT_SECRET'

##### Set logging to INFO or WARN
```
[...]
#logging settings
logging.level.root: INFO
#logging.level.edu.kit: TRACE
#logging.level.org.springframework: TRACE
#logging.level.org.springframework.amqp: DEBUG
[...]
```
## Install Authentication Service (Optional)
The authentication service is only needed when authentication is enabled.

[Install/Start authentication service](installation_authentication.md)

## Start KIT DM 2.0
After starting the database and the authentication service (if enabled) the repository can be started.
```bash=bash
user@localhost:/home/user/base-repo/$java -jar build/libs/base-repo.jar 

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.0.5.RELEASE)
[...]
Spring is running!
```

## More Information

* [KIT DM 2.0](https://github.com/kit-data-manager/base-repo.git)
* [Authentication Service](https://github.com/kit-data-manager/auth-service)
* [Docker](https://www.docker.com/)
* [Spring Boot Security](https://docs.spring.io/spring-boot/docs/2.0.5.RELEASE/reference/htmlsingle/#boot-features-security)


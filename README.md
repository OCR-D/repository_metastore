# KIT Data Manager - Metastore Service

This project contains metastore service microservice used by the KIT DM infrastructure. The service provides
metadata management, e.g. register xsd files, validate xml files, store/access/update metadata.

## How to build

In order to build this microservice you'll need:

* Java SE Development Kit 8 or higher

After obtaining the sources change to the folder where the sources are located perform the following steps:

```
user@localhost:/home/user/metastore-service$./gradlew build
BUILD SUCCESSFUL in 2s
4 actionable tasks: 4 executed
user@localhost:/home/user/metastore-service$
```

Finally, the actual microservice can be built. As a result, a fat jar containing the entire service is created at 'build/libs/metastore-service-0.1.0.jar'.


## How to start

Before you are able to start the repository microservice, you have to modify the application properties according to your local setup. 
Therefor, copy the file 'settings/application.yml' to your project folder and customize it. Special attentioned should be payed to the
properties in the 'datasource' section. (ToDo)

As soon as 'application.yml' is completed, you may start the repository microservice by executing the following command inside the project folder, 
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
1970-01-01 00:00:00.000  INFO 56918 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''

```

If your 'application.yml' is not located inside the project folder you can provide it using the command line argument --spring.config.location=<PATH_TO_APPLICATION.YML>
As soon as the microservice is started, you can browse to 

http://localhost:8080/swagger-ui.html

in order to see available RESTful endpoints and their documentation. Furthermore, you can use this Web interface to test single API calls in order to get familiar with the 
service.

## More Information

* [KIT Data Manager 2.0](https://github.com/kit-data-manager/base-repo)

## License

The KIT Data Manager is licensed under the Apache License, Version 2.0.
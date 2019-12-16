FROM openjdk:8-jdk-alpine
VOLUME /tmp
RUN mkdir -p /tmp/metastore/uploadDir
WORKDIR /usr/src/metastore
COPY . .
EXPOSE 8080
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "metastore-service-0.2.0.jar"]


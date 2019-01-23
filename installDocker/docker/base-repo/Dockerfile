FROM openjdk:8-jdk-alpine
VOLUME /tmp
RUN mkdir -p /kitdm/archive
WORKDIR /usr/src/base-repo
COPY . .
EXPOSE 8090
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "base-repo.jar"]


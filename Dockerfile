FROM openjdk:12-alpine
WORKDIR /galaxy
EXPOSE 9098 9099
COPY target/galaxy-1.0.jar galaxy.jar
COPY config.yml config.yml
ENTRYPOINT ["java" , "-jar" , "galaxy.jar", "server", "config.yml"]
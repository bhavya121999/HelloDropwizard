FROM openjdk:12-alpine
EXPOSE 9098
EXPOSE 9099
COPY target/galaxy-*.jar galaxy.jar
ENTRYPOINT ["java" , "-jar" , "galaxy.jar"]
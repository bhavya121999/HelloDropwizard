FROM openjdk:12-alpine
COPY target/galaxy-*.jar /galaxy.jar/
CMD ["java" , "-jar" , "/galaxy.jar"]
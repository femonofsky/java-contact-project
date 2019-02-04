FROM openjdk:8-jdk-alpine

MAINTAINER Femonofsky

COPY . /usr/src/java-code/
WORKDIR /usr/src/java-code/

COPY /target/*.jar ./app.jar

EXPOSE 8088
CMD ["java", "-jar", "app.jar"]
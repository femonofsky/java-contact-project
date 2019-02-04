FROM openjdk:8-jdk-alpine

MAINTAINER Femonofsky

COPY . /usr/src/java-code/
WORKDIR /usr/src/java-code

COPY /usr/src/java-code/target/*.jar ./app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]


FROM maven:3.6.0-jdk-10-slim
COPY . /usr/src/java-code/
RUN mvn packageWORKDIR

WORKDIR /usr/src/java-app
RUN cp /usr/src/java-code/target/*.jar ./app.jar
EXPOSE 8088
CMD ["java", "-jar", "app.jar"]
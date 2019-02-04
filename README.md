#  Contact Backend  - Java

This application receives a JSON body request and stores it on the disk using Google protobuff 
as the serializer and deserializer.

## Requirements 
* Java jdk 1.8
* Maven 3+


## Build
    
     ./mvnw clean install
     ./mvnw clean package
     
####  docker Compose

    docker-compose up

## Run
     ./run-script.sh


## Tests

    mvn clean test 
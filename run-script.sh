#!/bin/bash


echo "[START]"

# install dependencies projects
echo "[Load] dependencies"
./mvnw clean install

# build project
echo "[Build] generate .jar file"
./mvnw clean package


# run docker
docker-compose up
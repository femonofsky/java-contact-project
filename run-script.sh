#!/bin/bash
#


# build projects
echo "[START] backend build"
mvn package -f ./pom.xml


docker-compose  up
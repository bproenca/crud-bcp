#!/bin/bash
tag="v3"

echo "Working directory: $(pwd)"

echo "## Package JAR"
./mvnw clean package

echo "## RMI $tag"
docker rmi bproenca/crud-bcp:$tag

echo "## Build docker image"
docker build -f ./docker/Dockerfile -t bproenca/crud-bcp:$tag .

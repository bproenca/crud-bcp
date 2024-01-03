# Read Me First

Run local
```sh
./mvnw spring-boot:run -Dspring-boot.run.arguments=" \
--APP_TIME_DB=60000 \
--SPRING_DATASOURCE_USERNAME=? \
--SPRING_DATASOURCE_PASSWORD=? \
--SPRING_DATASOURCE_URL=?"
```

Docker
```sh
docker/build-image.sh
# create/edit docker/docker-compose.yml
docker-compose -f docker/local-docker-compose.yml up -d

docker logs -f docker_oci-app-bcp_1
docker-compose -f docker/local-docker-compose.yml down
```
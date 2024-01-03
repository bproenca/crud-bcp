# Read Me First

### Infra

To create tables, do the following in the 1st deploy:

```sh
# application.properties
spring.jpa.hibernate.ddl-auto=create
# add this param to startup
-Dspring-boot.run.profiles=xdev
```

### Run local

```sh
./mvnw spring-boot:run \
#-Dspring-boot.run.profiles=xdev \
-Dspring-boot.run.arguments=" \
    --server.port=8585
    --SPRING_DATASOURCE_USERNAME=? \
    --SPRING_DATASOURCE_PASSWORD=? \
    --SPRING_DATASOURCE_URL=?"
```

### Docker

```sh
docker/build-image.sh
# create/edit docker/docker-compose.yml
docker-compose -f docker/local-docker-compose.yml up -d

docker logs -f docker_oci-app-bcp_1
docker-compose -f docker/local-docker-compose.yml down
```

### Test

https://httpie.io/
```sh
http localhost:8585/crud/api/infra/info
```
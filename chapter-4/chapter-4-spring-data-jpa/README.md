## Start up Database
This application expects a PostgreSQL database running on localhost. You can use Docker to start the database:

```shell
docker run --ulimit memlock=-1:-1 -it --rm=true --memory-swappiness=0 --name chapter4_quarkus -e POSTGRES_USER=chapter4 -e POSTGRES_PASSWORD=chapter4 -e POSTGRES_DB=chapter4 -p 5432:5432 postgres:13
```

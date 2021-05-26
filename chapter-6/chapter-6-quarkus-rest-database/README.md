# Chapter 6 - Spring JPA on Quarkus

This project uses Spring JPA on Quarkus and a PostgreSQL database.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Start up Database
This application expects a PostgreSQL database running on localhost.
When running `quarkus:dev` (dev mode), the database will be automatically bootstrapped through [Quarkus DevServices](https://quarkus.io/guides/datasource#devservices-configuration-free-databases).
When running in `prod` mode (i.e. via `java -jar` or running the native image) you can use Docker to start the database:

```shell
docker run --ulimit memlock=-1:-1 -it --rm=true --memory-swappiness=0 --name chapter4 -p 5432:5432 quay.io/edeandrea/postgres-13-fruits:latest
```

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```
> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

When started, open the index page to create some `Fruits` and enjoy !

## Deploy on the cluster

TODO
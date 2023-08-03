# Chapter 4 - Spring Data JPA
This is an example using Spring Data JPA.

## Start up Database
This application expects a PostgreSQL database running on localhost. You can use Docker to start the database:

```shell
docker run -it --rm=true --name chapter4 -p 5432:5432 quay.io/edeandrea/fruits-postgres:latest
```

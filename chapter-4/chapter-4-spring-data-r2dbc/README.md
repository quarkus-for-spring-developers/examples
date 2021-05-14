# Chapter 4 - Spring Data R2DBC
This is an example using Spring Data R2DBC.

## Start up Database
This application expects a PostgreSQL database running on localhost. You can use Docker to start the database:

```shell
docker run --ulimit memlock=-1:-1 -it --rm=true --memory-swappiness=0 --name chapter4 -p 5432:5432 quay.io/edeandrea/postgres-13-fruits:latest
```

## Start up the Application
The application can be packaged using `./mvnw package`.

Navigate to the root directory and launch the application:

```shell script
cd chapter-4-spring-data-r2dbc
./mvnw spring-boot:run
```

Try to get fruits by running the following command with `curl`:

```
curl http://localhost:8080/fruits
```
You should get two fruits: 
```json
[
    {
        "description": "Hearty fruit",
        "id": 1,
        "name": "Apple"
    },
    {
        "description": "Juicy fruit",
        "id": 2,
        "name": "Pear"
    }
]
```
You can also add a fruit by running the following command:

```shell script
curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"description":"Best fruit ever","name":"Watermelon"}' \
  http://localhost:8080/fruits
```
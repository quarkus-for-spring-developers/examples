# Chapter 6 - Quarkus REST application & Remote Debug
This is a simple Quarkus JAX-RS project using RESTEasy Reactive that we will use to perform live coding.

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Package the application

Package the application and build/push the container image to docker hub, quay.io, ... using the following command
```shell script
./mvnw clean package
```

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

Next, check the URL of the “Hello” endpoint within your browser to see if your remote application is alive and replies  “http://chapter-6-quarkus-rest-debug.127.0.0.1.nip.io/hello”

chapter-5-quarkus-vertx-eventbus project
========================

This project illustrates how you can consume and produce events using Vert.x Event Bus.

`Quarkus` allows different beans to interact using asynchronous events, thus promoting loose-coupling. The messages are sent to virtual addresses. It offers 3 types of delivery mechanism:

* `point-to-point` - send the message, one consumer receives it. If several consumers listen to the address, a round robin is applied;

* `publish/subscribe` - publish a message, all the consumers listening to the address are receiving the message;

* `request/reply` - send the message and expect a response. The receiver can respond to the message in an asynchronous-fashion

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw compile quarkus:dev
```

Then, open your browser to `http://localhost:8080/async/{name]`, and you should see the Uppercase of {name}.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/chapter-5-quarkus-vertx-eventbus-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.html.

## Related guides

- RESTEasy Mutiny ([guide](https://quarkus.io/guides/getting-started-reactive#mutiny)): Mutiny support for RESTEasy server
- Eclipse Vert.x ([guide](https://quarkus.io/guides/vertx)): Write reactive applications with the Vert.x API

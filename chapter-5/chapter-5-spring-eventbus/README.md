chapter-5-spring-eventbus project
========================

This project illustrates how Spring Boot allows developers to consume and produce synchronous events by default.

To publish events, Spring Boot needs to create an event publisher for sending the event messages to listeners by injecting the ApplicationEventPublisher as well as using the `publishEvent()` API. Spring developers also need to create custom events that should extend `ApplicationEvent`. The custom events in Spring are synchronous by default so Spring developers need to create an `ApplicationEventMulticaster` in the configuration for handling asynchronous events or add an `@Async` annotation to the listener.

## Running the application

You can run your Spring boot application:

```shell script
./mvnw spring-boot:run
```

Then, you should see the published custom event:

```
Publishing custom event......
Received Spring Event: A test event message
```

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `chapter-5-spring-eventbus-0.0.1-SNAPSHOT.jar` file in the `target/` directory.

The application is now runnable using `java -jar target/chapter-5-spring-eventbus-0.0.1-SNAPSHOT.jar`.

## Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.4.4/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.4.4/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.4.4/reference/htmlsingle/#boot-features-developing-web-applications)

## Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)

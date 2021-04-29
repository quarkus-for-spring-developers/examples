chapter-5-spring-kafka-streams project
========================

This project illustrates how Spring Boot allows developers to consume and produce reactive stream messages by utilizing the Apache Kafka Streams APIs.

To produce reactive stream messages like the price generator, Spring developers need to implement `ProducerFactory` and `KafkaTemplate` beans with serialization logic. Then, the `KafkaTemplate` bean in `RestController` will be used to send the reactive messages to the Kafka cluster. 

## Spin up the Kafka cluster with Apache ZooKeeper by Docker Compose

```shell script
docker-compose up
```

## Install the Kafka cli (if not already present)
### macOS (via Homebrew)
```shell
brew install kafka
```

## Create Kafka Topic

```shell script
sh ./create-topics.sh
```

## Running the application

You can run your Spring boot application:

```shell script
./mvnw spring-boot:run
```

Then, you should see that the Kafka client is ready:

```
INFO 8602 --- [nio-8080-exec-1] o.a.kafka.common.utils.AppInfoParser     : Kafka version: 2.6.0
INFO 8602 --- [nio-8080-exec-1] o.a.kafka.common.utils.AppInfoParser     : Kafka commitId: 62abe01bee039651
INFO 8602 --- [nio-8080-exec-1] o.a.kafka.common.utils.AppInfoParser     : Kafka startTimeMs: 1618886455966
INFO 8602 --- [ad | producer-1] org.apache.kafka.clients.Metadata        : [Producer clientId=producer-1] Cluster ID: EApZO2XNSIaS6NONVRnKSg
```

## Produce and Consume the Event Message

Use `curl` command to send a message to the Kafka Topic:

```
curl localhost:8080/prices/10
``` 

Then, you should see the below logs in Spring Boot:

```
INFO 8602 --- [nio-8080-exec-1] org.acme.service.KafkaProducer           : Sent message: Price[10] to Kafka Topic[prices]
INFO 8602 --- [ntainer#0-0-C-1] org.acme.service.KafkaConsumer           : Received message: 10
```

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `chapter-5-spring-kafka-streams-0.0.1-SNAPSHOT.jar` file in the `target/` directory.

The application is now runnable using `java -jar target/chapter-5-spring-kafka-streams-0.0.1-SNAPSHOT.jar`.

## Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.4.4/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.4.4/maven-plugin/reference/html/#build-image)
* [Apache Kafka Streams Support](https://docs.spring.io/spring-kafka/docs/current/reference/html/_reference.html#kafka-streams)
* [Apache Kafka Streams Binding Capabilities of Spring Cloud Stream](https://docs.spring.io/spring-cloud-stream/docs/current/reference/htmlsingle/#_kafka_streams_binding_capabilities_of_spring_cloud_stream)
* [Spring for Apache Kafka](https://docs.spring.io/spring-boot/docs/2.4.4/reference/htmlsingle/#boot-features-kafka)

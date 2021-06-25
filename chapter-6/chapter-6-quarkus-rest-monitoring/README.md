# Chapter 6 - Quarkus REST application & Monitoring
This is a simple Quarkus JAX-RS project using RESTEasy Reactive that we will use to collect metric and traces.

This project uses Quarkus, the Supersonic Subatomic Java Framework. If you want to learn more about Quarkus, please visit its website: https://quarkus.io/.

## Check metrics locally

- You can check the metrics if you launch your quarkus application locally using the dev mode
```bash
./mvnw compile quarkus:dev
```
- Now, we should be able to call our service using `curl/httpie` tool:
```bash
http http://localhost:8080/hello/quarkus
```
- It should return `Hello!`
- So far so good, we can also see the metrics if you call the following url `http://localhost:8080/q/metrics`. You will get a long response message
  as the service will display different metrics such as: jvm, system, http server, etc metrics.
- To see the `greeting_counter` counter, grep the output of the response:
```bash
http http://localhost:8080/q/metrics | grep greeting
# HELP greeting_counter_total
# TYPE greeting_counter_total counter
greeting_counter_total{name="quarkus",} 1.0
```
**NOTE**: Call several the endpoint `http://localhost:8080/hello/quarkus` and next grab the metrics to see your incremented counter ;-)

## Collect distributed traces

- The first step to be done is now to start the tracing system jaeger to collect and display the captured traces:
```bash
docker run -p 5775:5775/udp -p 6831:6831/udp -p 6832:6832/udp -p 5778:5778 -p 16686:16686 -p 14268:14268 jaegertracing/all-in-one:latest
```
- Now, we are ready to run our application
```bash
./mvnw compile quarkus:dev
```
- Once both the application and tracing system are started, you can make a request to access the `quarkus hello` endpoint:
```bash
$ http http://localhost:8080/hello/quarkus
Hello!
```
- Then visit the Jaeger UI `http://localhost:16686/` to see the tracing information.
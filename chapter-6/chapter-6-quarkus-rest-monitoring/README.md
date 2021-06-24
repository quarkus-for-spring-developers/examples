# Chapter 6 - Quarkus REST application & Monitoring
This is a simple Quarkus JAX-RS project using RESTEasy Reactive that we will use to collect metric and traces.

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Package the application

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw clean package
```
**NOTE**: Uncomment the `quarkus.container-image` properties and change the values according to the registry where you will push the image

## Check metrics locally

- You can check the metrics if you launch your quarkus application locally
```bash
./mvnw compile quarkus:dev
```
- Now, we should be able to call our service:
```bash
curl http://localhost:8080/hello/quarkus
```
- It should return `Hello!`
- So far so good, we can also see the metrics at the following url `http://localhost:8080/q/metrics` where you should see the `greeting_counter counter`
```
# HELP greeting_counter_total
# TYPE greeting_counter_total counter
greeting_counter_total{name="quarkus",} 1.0
```

## Collect distributed traces

- The first step to be done is now to start the tracing system jaeger to collect and display the captured traces:
```
docker run -p 5775:5775/udp -p 6831:6831/udp -p 6832:6832/udp -p 5778:5778 -p 16686:16686 -p 14268:14268 jaegertracing/all-in-one:latest
```
- Now,  we are ready to run our application
```
./mvnw compile quarkus:dev
```
- Once both the application and tracing system are started, you can make a request to the provided endpoint:
```
$ curl http://localhost:8080/hello/quarkus
Hello!
```
- Then visit the Jaeger UI `http://localhost:16686/` to see the tracing information.
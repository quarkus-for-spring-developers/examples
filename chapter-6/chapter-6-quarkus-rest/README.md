# Chapter 6 - Quarkus JAX-RS RESTful application
This is a simple Quarkus JAX-RS project using RESTEasy Reactive.

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Package the application

Package the application and build/push the container image to docker hub, quay.io, ... using the following command
```shell script
./mvnw clean package
```

## Deploy the application on the Kubernetes platform

Deploy the application
```bash
kubectl create ns quarkus-demo
kubectl apply -f ./target/kubernetes/kubernetes.yml -n quarkus-demo
```
Access it using your browser pointing to the following url `http://chapter-6-quarkus-rest.127.0.0.1.nip.io`
**WARNING**: Change the domain name using the Ingress or OpenShift route address
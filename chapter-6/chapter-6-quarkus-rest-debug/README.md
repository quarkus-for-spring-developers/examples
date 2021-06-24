# Chapter 6 - Quarkus REST application & Remote Debug
This is a simple Quarkus JAX-RS project using RESTEasy Reactive that we will use to perform live coding.

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/.

The [Quarkus Kubernetes extension](https://quarkus.io/guides/deploying-to-kubernetes) will populate the Kubernetes manifest needed to deploy the application 
on a kubernetes cluster while the [Quarkus Container JIB extension](https://quarkus.io/guides/container-image) will build a container image using Google JIB Tool.

## Package the application

Package the application and build/push the container image to docker hub, quay.io, ... using the following command
```shell script
./mvnw clean package
```
**NOTE**: Uncomment the `quarkus.container-image` properties and change the values according to the registry where you will push the image

## Deploy the application on the Kubernetes platform

Deploy the application
```bash
kubectl create ns quarkus-demo-debug
kubectl apply -f ./target/kubernetes/kubernetes.yml -n quarkus-demo-debug
```
Access it using your browser pointing to the following url `http://chapter-6-quarkus-rest-debug.127.0.0.1.nip.io/hello`
**WARNING**: Change the domain name using the Ingress or OpenShift route address

## Launch the application locally

- Launch locally your quarkus application containing the code that you would like to verify or change remotely
```bash
./mvnw quarkus:remote-dev
```
- Do some code changes: java classes, ...
- Check again the response using the URL of the endpoint ;-). This new request triggers the recompilation of the code as can be seen in the console output:
```bash
2021-06-02 09:56:25,322 INFO  [io.qua.ver.htt.dep.dev.HttpRemoteDevClient] (Remote dev client thread) Sending dev/app/org/acme/GreeterResource.class
```
As soon as the code has been recompiled, it will be pushed to the remote application running as a linux container within a pod

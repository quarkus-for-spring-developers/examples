# Chapter 6 - Quarkus REST application & Spring Cloud Config server
This is a simple Quarkus JAX-RS project using RESTEasy Reactive.

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Install the Spring Cloud Config Server

If the `helm` tool is installed on your machine, you can deploy the SCC server on a kubernetes cluster
using the following commands
```shell script
kubectl create ns config-storage
helm install \
  spring-cloud-config-server kiwigrid/spring-cloud-config-server \
  -n config-storage \
  -f src/main/k8s/helm.yml
```
**NOTE**: The SCC server can be removed using the command `helm delete spring-cloud-config-server -n config-storage`

## Package the application

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw clean package
```

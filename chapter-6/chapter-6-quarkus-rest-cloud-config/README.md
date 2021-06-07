# Chapter 6 - Quarkus REST application & Spring Cloud Config server
This is a simple Quarkus JAX-RS project where the configuration has been externalized to
a github repository and where the Smallrye Client Config will fetch the information from a Spring Cloud Server.

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Deploy the Spring Cloud Config Server

If the `helm` tool has installed on your machine, you can deploy the SCC server on a kubernetes cluster
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

Package the application and build/push the container image to docker hub, quay.io, ... using the following command
```shell script
./mvnw clean package
```

## Deploy the application on the Kubernetes platform

Deploy the application
```bash
kubectl create ns quarkus-demo-cloud-config
kubectl apply -f ./target/kubernetes/kubernetes.yml -n quarkus-demo-cloud-config
```

Access it using your browser pointing to the following url `http://chapter-6-quarkus-rest-cloud-config.127.0.0.1.nip.io`
**WARNING**: Change the domain name using the Ingress or OpenShift route address

## Alternative

For local tests, you can launch the SCC server using docker 

```bash
docker run -it -p 8888:8888 \
      -e SPRING_CLOUD_CONFIG_SERVER_GIT_URI=https://github.com/ch007m/config-repo \
      -e SPRING_CLOUD_CONFIG_SERVER_GIT_DEFAULT-LABEL=main \
      -e SPRING_CLOUD_CONFIG_SERVER_GIT_SEARCHPATHS=chapter-6 \
      hyness/spring-cloud-config-server
```

In this case, it will be needed when you will start the quarkus application that you override the following parameter
to access the SCC server locally
```bash
./mvnw quarkus:dev -Dquarkus.spring-cloud-config.url=http://localhost:8888
```

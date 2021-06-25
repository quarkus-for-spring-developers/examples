# Chapter 6 - Quarkus REST application & Spring Cloud Config server
This is a simple Quarkus JAX-RS project where the configuration has been externalized to
a GitHub repository and where the Smallrye Client Config will fetch the information from a Spring Cloud Config Server.

This project uses Quarkus, the Supersonic Subatomic Java Framework. If you want to learn more about Quarkus, please visit its website: https://quarkus.io/.

The [Quarkus Kubernetes extension](https://quarkus.io/guides/deploying-to-kubernetes) will populate the Kubernetes manifest needed to deploy the application 
on a kubernetes cluster while the [Quarkus Container JIB extension](https://quarkus.io/guides/container-image) will build a container image using Google JIB Tool.

## Deploy the Spring Cloud Config Server

If the `helm` tool has installed on your machine, you can deploy the SCC server on a kubernetes cluster
using the following commands at the root of the `chapter-6-quarkus-rest-cloud-config` folder
```shell script
kubectl create ns config-storage
helm repo add kiwigrid https://kiwigrid.github.io
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
**NOTE**: Uncomment the `quarkus.container-image*` properties within the `application.properties` file and change the values according to the registry where you will push the image.
Instead of changing the properties within the file, you can also define them as such:

```bash
./mvnw clean package \
    -Dquarkus.container-image.registry=localhost:5000 \
    -Dquarkus.container-image.group=quarkus \
    -Dquarkus.container-image.tag=1.0 \
    -Dquarkus.container-image.build=true \
    -Dquarkus.container-image.push=true \
    -Dquarkus.container-image.insecure=true \
    -Dquarkus.container-image.name=chapter-6-quarkus-rest-cloud-config
```

## Deploy the application on the Kubernetes platform

Deploy the application
```bash
kubectl create ns quarkus-demo-cloud-config
kubectl apply -f ./target/kubernetes/kubernetes.yml -n quarkus-demo-cloud-config
```

Access it using your browser pointing to the following url `http://chapter-6-quarkus-rest-cloud-config.127.0.0.1.nip.io/hello`
**WARNING**: Change the domain name using the Ingress or OpenShift route address

You will see as response message
```text
Hello cloud config prod quarkus!
```

## Alternative

For local tests, you can launch the SCC server using docker 

```bash
docker run -it -p 8888:8888 \
      -e SPRING_CLOUD_CONFIG_SERVER_GIT_URI=https://github.com/quarkus-for-spring-developers/sccs-config-repo \
      -e SPRING_CLOUD_CONFIG_SERVER_GIT_DEFAULT-LABEL=main \
      -e SPRING_CLOUD_CONFIG_SERVER_GIT_SEARCHPATHS={application} \
      hyness/spring-cloud-config-server
```

In this case, it will be needed when you will start the quarkus application that you override the following parameter
to access the SCC server locally
```bash
./mvnw quarkus:dev -Dquarkus.spring-cloud-config.url=http://localhost:8888
```

## Clean up
```bash
NAMESPACE=quarkus-demo-cloud-config
kubectl delete -f ./target/kubernetes/kubernetes.yml -n $NAMESPACE
kubectl delete ns $NAMESPACE
```

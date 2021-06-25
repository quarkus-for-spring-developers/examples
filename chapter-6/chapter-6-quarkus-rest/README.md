# Chapter 6 - Quarkus JAX-RS RESTful application
This is a simple Quarkus JAX-RS project using RESTEasy Reactive.

This project uses Quarkus, the Supersonic Subatomic Java Framework. If you want to learn more about Quarkus, please visit its website: https://quarkus.io/.

The [Quarkus Kubernetes extension](https://quarkus.io/guides/deploying-to-kubernetes) will populate the Kubernetes manifest needed to deploy the application 
on a kubernetes cluster while the [Quarkus Container JIB extension](https://quarkus.io/guides/container-image) will build a container image using Google JIB Tool.

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
    -Dquarkus.container-image.name=chapter-6-quarkus-rest
```

## Deploy the application on the Kubernetes platform

Deploy the application
```bash
kubectl create ns quarkus-demo
kubectl apply -f ./target/kubernetes/kubernetes.yml -n quarkus-demo
```
### Port forwarding
To access the service running within the Kubernetes, execute the following command respnsible to fowardthe traffic from your locallhost to the 
proxied service:

```bash
kubectl port-forward -n quarkus-demo service/chapter-6-quarkus-rest 8888:80
```

curl or http your service
```bash
http :8888/fruits

HTTP/1.1 200 OK
Content-Type: application/json
content-length: 99

[
    {
        "description": "Winter fruit",
        "name": "Apple"
    },
    {
        "description": "Tropical fruit",
        "name": "Pineapple"
    }
]
```

### Using ingress

Uncomment the following properties within the `application.properties` file.
```
quarkus.kubernetes.ingress.expose=true
quarkus.kubernetes.ingress.host=chapter-6-quarkus-rest.127.0.0.1.nip.io
```
**WARNING**: Change the IP address of the host depending the cluster used: kind, minikube, ...

Recompile
```bash
./mvnw clean package ....
```

Redeploy the project
```bash
kubectl delete -f ./target/kubernetes/kubernetes.yml -n quarkus-demo
kubectl apply -f ./target/kubernetes/kubernetes.yml -n quarkus-demo
```
Next, access the service using your browser pointing to the following url `http://chapter-6-quarkus-rest.127.0.0.1.nip.io/fruits`

curl or http your service
```bash
http http://chapter-6-quarkus-rest.127.0.0.1.nip.io/fruits
HTTP/1.1 200 OK
Connection: keep-alive
Content-Length: 99
Content-Type: application/json
Date: Thu, 24 Jun 2021 16:23:12 GMT

[
    {
        "description": "Winter fruit",
        "name": "Apple"
    },
    {
        "description": "Tropical fruit",
        "name": "Pineapple"
    }
]
```

## Clean up
```bash
NAMESPACE=quarkus-demo
kubectl delete -f ./target/kubernetes/kubernetes.yml -n $NAMESPACE
kubectl delete ns $NAMESPACE
```


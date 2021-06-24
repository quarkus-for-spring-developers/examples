# Chapter 6 - Quarkus REST application & config
This is a simple Quarkus JAX-RS project where the configuration has been externalized
using ENV variables passed as Kubernetes parameters or using a ConfigMap.

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

## Using ConfigMap

If you plan to use a ConfigMap or Secret, then it is needed to :

- Create a file which contains the properties locally (e.g src/main/configs/greeting-env).
```
greeting.message=Hello configmap
greeting.name=quarkus
```
- The 2 properties should be removed too from the `application.properties` file
- Next, you can create a `ConfigMap` using the content of this file with the help of this command: 
```bash
kubectl create ns quarkus-demo-config
kubectl create -n quarkus-demo-config configmap greeting-map --from-env-file=src/main/configs/greeting-env
```
- To generate the proper Kubernetes Deployment MANIFEST, we will use a different property able to generate the field `envFrom` using as `configMapRef`.
- So comment within the properties file the following properties
```
#quarkus.kubernetes.env.vars."greeting.message"=Hello from Kubernetes to
#quarkus.kubernetes.env.vars."greeting.name"=Quarkus developers
```
- And add this one 
```
quarkus.kubernetes.env.configmaps=greeting-map
```

## Deploy the application on the Kubernetes platform

Deploy the application
```bash
kubectl apply -f ./target/kubernetes/kubernetes.yml -n quarkus-demo-config
```
Access it using your browser pointing to the following url `http://chapter-6-quarkus-rest-config.127.0.0.1.nip.io/hello`
**WARNING**: Change the domain name using the Ingress or OpenShift route address
# Chapter 6 - Quarkus REST application & config
This is a simple Quarkus JAX-RS project where some parameters (aka configuration) have been externalized
using environment variables passed to the Kubernetes's pod or using a ConfigMap.

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
    -Dquarkus.container-image.name=chapter-6-quarkus-rest-config
```
## Using Kubernetes Environment variable

To pass the parameters to the container using some `Env`variables, then uncomment the following lines within the `application.properties` file
```text
quarkus.kubernetes.env.vars."greeting.message"=Hello from Kubernetes Env var to
quarkus.kubernetes.env.vars."greeting.name"=Quarkus developers
```
Recompile the application and generate new kubernetes manifest files
```bash
./mvnw clean package ...
```
Deploy the project on the cluster
```bash
kubectl create ns quarkus-demo-config
kubectl apply -f ./target/kubernetes/kubernetes.yml -n quarkus-demo-config
```
Call the service/endpoint using curl/httpie or simply your browser
```bash
http http://chapter-6-quarkus-rest-config.127.0.0.1.nip.io/hello           
HTTP/1.1 200 OK
Connection: keep-alive
Content-Length: 52
Content-Type: text/plain
Date: Fri, 25 Jun 2021 08:40:08 GMT

Hello from Kubernetes Env var to Quarkus developers!
```

## Using Kubernetes EnvFrom and ConfigMap

If you plan to use a ConfigMap or Secret, then it is needed to :

- Create a file which contains the properties (e.g `src/main/configs/greeting-env`).
```
greeting.message=Hello configmap
greeting.name=quarkus
```
- The 2 properties should be removed too from the `application.properties` file (or commented)
  ```text
  quarkus.kubernetes.env.vars."greeting.message"=Hello from Kubernetes Env var to
  quarkus.kubernetes.env.vars."greeting.name"=Quarkus developers
  ```
- Next, you can create a `ConfigMap` using the content of the `greeting-env` file with the help of this command: 
```bash
kubectl create -n quarkus-demo-config configmap greeting-map --from-env-file=src/main/configs/greeting-env
```
- To generate the proper Kubernetes Deployment manifest, we will use a different property able to generate the field `envFrom` using as `configMapRef`.
- Uncomment the following property
```
# quarkus.kubernetes.env.configmaps=greeting-map
```
- Recompile the application usign the command `./mvnw clean package ...` and redeploy the application
```bash
kubectl delete -f ./target/kubernetes/kubernetes.yml -n quarkus-demo-config
kubectl apply -f ./target/kubernetes/kubernetes.yml -n quarkus-demo-config
```
- Access it using your browser pointing to the following url `http://chapter-6-quarkus-rest-config.127.0.0.1.nip.io/hello`
**WARNING**: Change the domain name using the Ingress or OpenShift route address

- or `curl/httpie` it
```bash
http http://chapter-6-quarkus-rest-config.127.0.0.1.nip.io/hello
HTTP/1.1 200 OK
Connection: keep-alive
Content-Length: 24
Content-Type: text/plain
Date: Fri, 25 Jun 2021 08:44:42 GMT

Hello configmap quarkus!
```

## Clean up
```bash
NAMESPACE=quarkus-demo-config
kubectl apply -f ./target/kubernetes/kubernetes.yml -n $NAMESPACE
kubectl delete ns $NAMESPACE
```
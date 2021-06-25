# Chapter 6 - Quarkus Hibernate ORM and PostGreSQL DB

This project uses a Quarkus Hibernate ORM project connected to a PostgreSQL database running on a kubernetes cluster.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/.

The [Quarkus Kubernetes extension](https://quarkus.io/guides/deploying-to-kubernetes) will populate the Kubernetes manifest needed to deploy the application 
on a kubernetes cluster while the [Quarkus Container JIB extension](https://quarkus.io/guides/container-image) will build a container image using Google JIB Tool.

## Start up Database locally

This application expects a PostgreSQL database running on localhost.
When running `quarkus:dev` (dev mode), the database will be automatically bootstrapped through [Quarkus DevServices](https://quarkus.io/guides/datasource#devservices-configuration-free-databases).
When running in `prod` mode (i.e. via `java -jar` or running the native image) you can use Docker to start the database:

```shell
docker run --ulimit memlock=-1:-1 -it --rm=true --memory-swappiness=0 --name chapter6 -p 5432:5432 quay.io/edeandrea/postgres-13-fruits:latest
```

## Running the application in dev mode

To check if the project is working fine locally, we will launch Quarkus in Dev mode
```shell script
./mvnw compile quarkus:dev
```

When started, open the index page `http://localhost:8080` to create some `Fruits` and enjoy !

## Generate the container image and Kubernetes manifest

If the project is working fine locally, we can now build the image and generate the Kubernetes manifest files

For that purpose, uncomment the `quarkus.container-image*` properties within the `application.properties` file and change the values according to the registry where you will push the image.
Instead of changing the properties within the file, you can also define them as such:

```bash
./mvnw clean package \
    -Dquarkus.container-image.registry=localhost:5000 \
    -Dquarkus.container-image.group=quarkus \
    -Dquarkus.container-image.tag=1.0 \
    -Dquarkus.container-image.build=true \
    -Dquarkus.container-image.push=true \
    -Dquarkus.container-image.insecure=true \
    -Dquarkus.container-image.name=chapter-6-quarkus-rest-db
```
## Deploy the application on the Kubernetes platform

- Install first the `PostgreSQL database` using the image built for this ebook as it includes the needed SQL schema able to create the `fruits` table used by our example
- Execute the following command at the root of the example `chapter-6-quarkus-rest-database`
```bash
kubectl create ns quarkus-demo-db
kubectl apply -n quarkus-demo-db -f ./k8s/postgresql.yaml
```
- Next, create the secret containing the properties to bind to the application to access the DB
```bash
kubectl apply -n quarkus-demo-db -f ./k8s/service-binding-secret.yaml
```
- Deploy the application able to access the DB using the Service Binding
```bash
kubectl apply -f ./target/kubernetes/kubernetes.yml -n quarkus-demo-db
```
Access it using your browser pointing to the following url `http://chapter-6-quarkus-rest-database.127.0.0.1.nip.io/`
**WARNING**: Change the domain name using the Ingress or OpenShift route address

## Clean up
```bash
NAMESPACE=quarkus-demo-db
kubectl delete -f ./target/kubernetes/kubernetes.yml -n $NAMESPACE
kubectl delete ns $NAMESPACE
```
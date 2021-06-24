# Chapter 6 - Spring JPA on Quarkus

This project uses Spring JPA on Quarkus and a PostgreSQL database.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Start up Database

This application expects a PostgreSQL database running on localhost.
When running `quarkus:dev` (dev mode), the database will be automatically bootstrapped through [Quarkus DevServices](https://quarkus.io/guides/datasource#devservices-configuration-free-databases).
When running in `prod` mode (i.e. via `java -jar` or running the native image) you can use Docker to start the database:

```shell
docker run --ulimit memlock=-1:-1 -it --rm=true --memory-swappiness=0 --name chapter4 -p 5432:5432 quay.io/edeandrea/postgres-13-fruits:latest
```

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```
**NOTE**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

**NOTE**: Uncomment the `quarkus.container-image` properties and change the values according to the registry where you will push the image

When started, open the index page to create some `Fruits` and enjoy !

## Deploy the application on the Kubernetes platform

- Install first the `PostgreSQL database` using the image built for this ebook as it includes the needed SQL schema able to create the `fruits` table used by our example
- Execute the following command from the project cloned
```bash
kubectl create ns quarkus-demo-db
kubectl apply -n quarkus-demo-db -f ./k8s/postgresql.yaml
```
- Next, create the secret containing the properties to bind to the application to access the DB
```bash
kubectl apply -n quarkus-demo-db -f ./k8s/service-binding-secret.yaml
```

- Deploy the application
```bash
kubectl apply -f ./target/kubernetes/kubernetes.yml -n quarkus-demo-db
```
Access it using your browser pointing to the following url `http://chapter-6-quarkus-rest-database.127.0.0.1.nip.io/api/fruits`
**WARNING**: Change the domain name using the Ingress or OpenShift route address
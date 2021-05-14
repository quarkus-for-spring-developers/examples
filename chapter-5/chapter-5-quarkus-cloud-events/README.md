chapter-5-quarkus-cloud-events project
========================

This project illustrates how you can bind Knative Events using Funqy extension. The project also contains a single function: `functions.Function.function()`, the function just returns its argument.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw compile quarkus:dev
```

## The `func` CLI

It's recommended to set `FUNC_REGISTRY` environment variable.
```shell script
# replace ~/.bashrc by your shell rc file
# replace docker.io/johndoe with your registry
export FUNC_REGISTRY=docker.io/johndoe
echo "export FUNC_REGISTRY=docker.io/johndoe" >> ~/.bashrc 
```

## Sending CloudEvent messages

Do not forget to set `URL` variable to the route of your function in `Kubernetes` cluster or `Local` environment.

### cURL

```shell script
URL=http://localhost:8080/
curl -v ${URL} \
  -H "Content-Type:application/json" \
  -H "Ce-Id:1" \
  -H "Ce-Source:cloud-event-example" \
  -H "Ce-Type:dev.knative.example" \
  -H "Ce-Specversion:1.0" \
  -d "{\"message\": \"hello\"}\""
```

### HTTPie

```shell script
URL=http://localhost:8080/
http -v ${URL} \
  Content-Type:application/json \
  Ce-Id:1 \
  Ce-Source:cloud-event-example \
  Ce-Type:dev.knative.example \
  Ce-Specversion:1.0 \
  message=hello
```

Then, you should see the similar logs below:

```
{"message":"HELLO"}
```

## Deploying your function to Kubernetes via func CLI

Quarkus functions can be created and managed using the CLI interactively, scripted, or by direct integration with the client library. The Function Developer's Guideand examples herein demonstrate the CLI-based approach. Install the latest CLI [here](https://github.com/boson-project/func/blob/main/docs/installing_cli.md).

### Building

This command builds OCI image for the function.

```shell script
func build                  # build jar
func build --builder native # build native binary
```

### Running

This command runs the function locally in a container using the image created above.

```shell script
func run
```

### Deploying

This commands will build and deploy the function into cluster.

```shell script
func deploy # also triggers build
```

## Cleanup

To remove the deployed function from your cluster, run:

```shell
func delete
```
chapter-5-spring-cloud-events project
========================

This sample project contains a single function based on Spring Cloud Function: `functions.CloudFunctionApplication.uppercase()`, which returns the uppercase of the data passed via CloudEvents.

## Local execution

Make sure that `Java 11 SDK` is installed.

To start server locally run `./mvnw spring-boot:run`.
The command starts http server and automatically watches for changes of source code.
If source code changes the change will be propagated to running server. It also opens debugging port `5005`
so a debugger can be attached if needed.

To run tests locally run `./mvnw test`.

## The `func` CLI

The [func CLI](https://github.com/boson-project/func) is a new open source client library and command-line interface tool for the development of platform-agnostic functions on any Kubernetes cluster. Install the [func tool](https://github.com/boson-project/func/blob/main/docs/installing_cli.md) on your OS (e.g. Linux, macOS, and Windows). Then make sure to log in to a Kubernetes cluster where you need to deploy the function.

It's recommended to set `FUNC_REGISTRY` environment variable.

```shell script
# replace ~/.bashrc by your shell rc file
# replace docker.io/johndoe with your registry
export FUNC_REGISTRY=docker.io/johndoe
echo "export FUNC_REGISTRY=docker.io/johndoe" >> ~/.bashrc
```

### Building

This command builds an OCI image for the function.

```shell script
func build -v                # build jar
```

### Running

This command runs the func locally in a container
using the image created above.

```shell script
func run
```

### Deploying

This command will build and deploy the function into cluster.

```shell script
func deploy -v # also triggers build
```

## Function invocation

For the examples below, please be sure to set the `URL` variable to the route of your function.

You get the route by following command.

```shell script
func describe
```

Note the value of **Routes:** from the output, set `$URL` to its value.

__TIP__:

If you use `kn` then you can set the url by:

```shell script
# kn service describe <function name> and show route url
export URL=$(kn service describe $(basename $PWD) -ourl)
```

### cURL

```shell script
URL=http://localhost:8080/
curl -v ${URL} \
  -H "Content-Type:application/json" \
  -H "Ce-Id:1" \
  -H "Ce-Source:cloud-event-example" \
  -H "Ce-Type:dev.knative.example" \
  -H "Ce-Specversion:1.0" \
  -d "{\"input\": \"hello\"}\""
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
  input=hello
```

Then, you should see the similar output below:

```
{
    "error": null,
    "input": "hello",
    "operation": null,
    "output": "HELLO"
}
```

## Cleanup

To remove the deployed function from your cluster, run:

```shell
func delete
```

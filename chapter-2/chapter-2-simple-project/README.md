# Chapter 2 - Simple Quarkus Project
This is a simple Quarkus JAX-RS example.

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Project generation and run

#### Generate project
1. Navigate to https://code.quarkus.io in your browser.
1. In the Artifact section, enter chapter-2-simple-project.
1. In the list of extensions under Web, select RESTEasy JAX-RS.
1. Click the Generate your application button at the top.
1. You will be prompted to download the project. Save it somewhere on your computer.
1. Once downloaded, extract the contents of chapter-2-simple-project.zip into a folder. A directory called chapter-2-simple-project will be created.

#### Running the application in dev mode

Navigate to the `chapter-2-simple-project` directory and launch the application.

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

Open browser to http://localhost:8080/hello-resteasy


## Configuration

1. In the resource class, add 
    ```java
    @ConfigProperty(name = "greeting.name")
    String greeting;
    ```
1. Change the hello method to use the greeting name:
    ```java
    @GET
    	@Produces(MediaType.TEXT_PLAIN)
    	public String hello() {
    		return "Hello " + this.greeting;
    	}
    ```    

1. Open the `application.properties` file and add:
    ```properties
    greeting.name=Quarkus (properties)
    ``` 
1. Open browser to http://localhost:8080/hello-resteasy, you should see Hello Quarkus (properties)

## Profiles

1. Edit the `application.properties` file and add these three lines:
    ```
    %dev.greeting.name=Quarkus for Spring Developer (dev)
    %prod.greeting.name=Quarkus for Spring Developer (prod)
    %test.greeting.name=RESTEasy    
    ```
1. Refresh the browser to http://localhost:8080/hello-resteasy, you should now see Quarkus for Spring Developer (dev).

## Dependency injection

1. Create class `GreetingService` in the `org.acme` package with the following content:
    ```java
    @ApplicationScoped
    public class GreetingService {
        private final String greeting;
    
        public GreetingService(@ConfigProperty(name = "greeting.name") String greeting) {
            this.greeting = greeting;
        }
    
        public String getGreeting() {
            return "Hello " + this.greeting;
        }
    }
    ```

1. Refactor the content of `GreetingResource` to use the new `GreetingService` bean:
    ```java
    @Path("/hello-resteasy")
    public class GreetingResource {
    
        private final GreetingService greetingService;
    
        public GreetingResource(GreetingService greetingService) {
            this.greetingService = greetingService;
        }
    
        @GET
        @Produces(MediaType.TEXT_PLAIN)
        public String hello() {
            return greetingService.getGreeting();
        }
    }
    ```

3. Open browser to http://localhost:8080/hello-resteasy

## Testing

1. Create class `GreetingServiceTest` in the src/test/java/org/acme folder with the following content:
    ```java
    class GreetingServiceTest {
        @Test
        void getGreetingOk() {
            Assertions.assertEquals("Hello Quarkus", new GreetingService("Quarkus").getGreeting());
        }
    }
    ```

1. You can launch the test by running the following command:
    ```shell script
    ./mvnw verify
    ```
1. Refactor the GreetingResourceTest class test to become:
    ```java
    @QuarkusTest
    public class GreetingResourceTest {
        @InjectMock
        GreetingService greetingService;
    
        @Test
        @DisabledOnNativeImage
        public void testHelloEndpoint() {
            Mockito.when(this.greetingService.getGreeting()).thenReturn("Hello Quarkus");
    
            given()
                .when().get("/hello-resteasy")
                .then()
                    .statusCode(200)
                    .body(is("Hello Quarkus"));
    
            Mockito.verify(this.greetingService).getGreeting();
            Mockito.verifyNoMoreInteractions(this.greetingService);
        }
    }
    ```
1. Return to the command line and re-run
    ```shell script
    ./mvnw verify
    ```

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `chapter-2-simple-project-1.0.0-SNAPSHOT.jar` file in the `/target` directory  containing just the classes and resources of the projects, it is not the runnable jar.
It produces also the `quarkus-app` directory with contains the `quarkus-run.jar`. Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib` directory.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/chapter-2-simple-project-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.html.

# RESTEasy JAX-RS

<p>A Hello World RESTEasy resource</p>

Guide: https://quarkus.io/guides/rest-json

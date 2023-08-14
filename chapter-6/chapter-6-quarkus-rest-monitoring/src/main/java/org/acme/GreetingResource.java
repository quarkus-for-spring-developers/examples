package org.acme;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;

@Path("/hello")
public class GreetingResource {
    private final MeterRegistry registry;

    public GreetingResource(MeterRegistry registry) {
        this.registry = registry;
    }

    @GET
    @Path("/{name}")
    public String sayHello(@PathParam(value = "name") String name) {
        this.registry.counter("greeting_counter", Tags.of("name", name)).increment();
        return "Hello!";
    }
}

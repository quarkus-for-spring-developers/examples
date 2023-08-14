package org.acme;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/hello")
public class GreeterResource {
    private final GreetingProperties greetingProperties;

    public GreeterResource(GreetingProperties greetingProperties) {
        this.greetingProperties = greetingProperties;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return this.greetingProperties.message() + " " + this.greetingProperties.name().orElse("world") + this.greetingProperties.suffix();
    }
}

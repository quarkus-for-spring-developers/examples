package org.acme;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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

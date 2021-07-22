package org.acme;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.eventbus.EventBus;
import io.vertx.mutiny.core.eventbus.Message;

@Path("/async")
public class GreetingResource {

    private final EventBus bus;

    public GreetingResource(EventBus bus) {
      this.bus = bus;
    }                                   
     
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{name}")
    public Uni<String> greeting(@PathParam("name") String name) {
        return this.bus.<String>request("greeting", name)
                .map(Message::body);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("block/{message}")
    public Uni<String> blockingConsumer(String message) {
        return this.bus.<String>request("blocking-consumer", message)
                 .map(Message::body);
    }
   
}

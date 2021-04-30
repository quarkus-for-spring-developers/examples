package org.acme;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.eventbus.EventBus;
import io.vertx.mutiny.core.eventbus.Message;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
        return bus.<String>request("greeting", name)        
                 .onItem().transform(Message::body);
    }
   
}
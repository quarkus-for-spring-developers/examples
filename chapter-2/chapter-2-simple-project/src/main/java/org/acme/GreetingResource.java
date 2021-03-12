package org.acme;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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

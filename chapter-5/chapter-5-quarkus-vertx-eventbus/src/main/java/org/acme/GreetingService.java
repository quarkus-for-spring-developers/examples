package org.acme;

import io.smallrye.common.annotation.Blocking;
import io.quarkus.vertx.ConsumeEvent;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GreetingService {

    @ConsumeEvent("greeting")                        
    public String consume(String name) {    
        return name.toUpperCase();
    }

    @ConsumeEvent("blocking-consumer")
    @Blocking
    public String consumeBlocking(String message) {
        return "Processing Blocking I/O: " + message;
    }

}
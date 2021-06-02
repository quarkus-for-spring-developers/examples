package org.acme.function;

import org.acme.domain.Input;
import org.acme.domain.Output;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import io.quarkus.funqy.Context;
import io.quarkus.funqy.Funq;
import io.quarkus.funqy.knative.events.CloudEvent;

public class ToUppercaseFunction {

    private static final Logger LOGGER = LoggerFactory.getLogger(ToUppercaseFunction.class);

    @Funq("uppercase")
    public Output function(Input input, @Context CloudEvent cloudEvent) {
        if (cloudEvent != null) {
            LOGGER.info("Input CE Id: {}", cloudEvent.id());
            LOGGER.info("Input CE Spec Version: {}", cloudEvent.specVersion());
            LOGGER.info("Input CE Source: {}", cloudEvent.source());
            LOGGER.info("Input CE Subject: {}", cloudEvent.subject());
        }
        return new Output(input.getMessage().toUpperCase());
    }

}

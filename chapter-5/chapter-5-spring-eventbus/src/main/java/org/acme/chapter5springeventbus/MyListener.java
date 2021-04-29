package org.acme.chapter5springeventbus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MyListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyListener.class);

    @EventListener
    public void handleEvent(MySpringEvent event) {
        LOGGER.info("Received Spring Event: {}", event.getMessage());
    }
}

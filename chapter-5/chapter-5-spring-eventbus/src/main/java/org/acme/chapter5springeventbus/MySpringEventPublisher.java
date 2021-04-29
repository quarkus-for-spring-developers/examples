package org.acme.chapter5springeventbus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class MySpringEventPublisher {
    private static final Logger LOGGER = LoggerFactory.getLogger(MySpringEventPublisher.class);
    private final ApplicationEventPublisher applicationEventPublisher;

    public MySpringEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishCustomEvent(String message) {
        LOGGER.info("Publishing custom event with message = {}", message);
        MySpringEvent myEvent = new MySpringEvent(message);
        this.applicationEventPublisher.publishEvent(myEvent);
    }

}

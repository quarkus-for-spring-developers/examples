package org.acme.chapter5springeventbus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class MySpringEventPublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void publishCustomEvent(final String message) {
        System.out.println("Publishing custom event......");
        final MySpringEvent myEvent = new MySpringEvent(this, message);
        applicationEventPublisher.publishEvent(myEvent);
    }

}
package org.acme.chapter5springeventbus;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class MySpringEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public MySpringEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishCustomEvent(String message) {
        System.out.println("Publishing custom event......");
        MySpringEvent myEvent = new MySpringEvent(message);
        this.applicationEventPublisher.publishEvent(myEvent);
    }

}

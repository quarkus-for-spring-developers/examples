package org.acme.chapter5springeventbus;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MyListener {
    @EventListener
    public void handleEvent(MySpringEvent event) {
        System.out.println("Received Spring Event: " + event.getMessage());
    }
}

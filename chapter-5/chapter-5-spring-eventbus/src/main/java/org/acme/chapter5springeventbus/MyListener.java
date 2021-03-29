package org.acme.chapter5springeventbus;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class MyListener implements ApplicationListener<MySpringEvent> {

    @Override
    public void onApplicationEvent(final MySpringEvent event) {
        System.out.println("Received Spring Event: " + event.getMessage());
    }

}
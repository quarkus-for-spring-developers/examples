package org.acme.chapter5springeventbus;

import org.springframework.context.ApplicationEvent;

public class MySpringEvent extends ApplicationEvent {

    private String message;

    public MySpringEvent(final Object source, final String message) {
        super(source);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
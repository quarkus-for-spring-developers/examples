package org.acme.chapter5springeventbus;

public class MySpringEvent {
    private String message;

    public MySpringEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

}

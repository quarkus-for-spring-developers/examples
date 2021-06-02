package org.acme.domain;

public class Input {
    private String message;

    public Input() {}

    public Input(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

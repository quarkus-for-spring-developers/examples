package org.acme.domain;

public class Output {
    private String message;

    public Output() {}

    public Output(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

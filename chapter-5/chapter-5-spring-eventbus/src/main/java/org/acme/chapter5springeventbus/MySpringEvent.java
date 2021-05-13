package org.acme.chapter5springeventbus;

import java.util.Objects;
import java.util.StringJoiner;

public class MySpringEvent {
    private final String message;

    public MySpringEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MySpringEvent that = (MySpringEvent) o;
        return Objects.equals(this.message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.message);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", MySpringEvent.class.getSimpleName() + "[", "]")
          .add("message='" + this.message + "'")
          .toString();
    }
}

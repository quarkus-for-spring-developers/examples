package org.acme.chapter5springeventbus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MyListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyListener.class);
    private final List<MySpringEvent> events = new ArrayList<>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock(true);

    @EventListener
    public void handleEvent(MySpringEvent event) {
        LOGGER.info("Received Spring Event: {}", event.getMessage());
        Lock writeLock = this.lock.writeLock();
        writeLock.lock();

        try {
            this.events.add(event);
        }
        finally {
            writeLock.unlock();
        }
    }
}

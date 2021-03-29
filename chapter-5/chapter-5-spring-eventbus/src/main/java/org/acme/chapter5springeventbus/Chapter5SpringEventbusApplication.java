package org.acme.chapter5springeventbus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Chapter5SpringEventbusApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(Chapter5SpringEventbusApplication.class, args);
		MySpringEventPublisher mySpringEventPublisher = applicationContext.getBean(MySpringEventPublisher.class);
        mySpringEventPublisher.publishCustomEvent("A test message");
	}

}

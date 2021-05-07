package org.acme.config;

import org.acme.service.InMemoryChannel;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InMemoryChannelConfig {
	@Bean
	public InMemoryChannel<Double> inMemoryChannel() {
		return new InMemoryChannel<>();
	}
}

package org.acme;

import org.springframework.boot.SpringApplication;

public class TestApplication {
	public static void main(String[] args) {
		SpringApplication
			.from(Chapter4SpringDataR2dbcApplication::main)
			.with(ContainersConfig.class)
			.run(args);
	}
}

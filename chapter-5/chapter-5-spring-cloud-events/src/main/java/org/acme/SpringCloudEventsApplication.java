package org.acme;

import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.function.cloudevent.CloudEventHeaderEnricher;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringCloudEventsApplication {
  public static void main(String[] args) {
    SpringApplication.run(SpringCloudEventsApplication.class, args);
  }

  @Bean
  public CloudEventHeaderEnricher attributesProvider() {
    return attributes -> attributes
        .setSpecVersion("1.0")
        .setId(UUID.randomUUID().toString())
        .setSource("http://example.com/uppercase")
        .setType("com.redhat.faas.springboot.events");
  }
}

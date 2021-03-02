package org.acme.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenAPIConfig {
	@Bean
	public OpenAPI openApi() {
		return new OpenAPI()
			.info(getInfo());
	}

	private Info getInfo() {
		return new Info()
			.title("Fruits API")
			.version("1.0.0")
			.description("Example Fruit Service - Spring WebFlux")
			.license(
				new License()
					.name("Apache 2.0")
					.url("https://www.apache.org/licenses/LICENSE-2.0.html")
			)
			.contact(
				new Contact()
					.name("Eric Deandrea")
					.email("edeandrea@redhat.com")
					.url("https://developers.redhat.com/blog/author/edeandrea")
			);
	}
}

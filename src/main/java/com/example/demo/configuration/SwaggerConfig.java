package com.example.demo.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info().title("Product API").version("1.0")
						.description("Spring Boot Swagger Basic Auth Example"))
				.addSecurityItem(new SecurityRequirement().addList("basicAuth"))
				.components(new Components().addSecuritySchemes("basicAuth",
						new SecurityScheme().name("basicAuth").type(SecurityScheme.Type.HTTP).scheme("basic")));
	}
}

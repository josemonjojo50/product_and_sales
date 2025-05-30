package com.retail.productsales.configuration;

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
				.info(new Info().title("Product and Sales Management API").version("1.0")
						.description("This Spring Boot application provides RESTful APIs for managing products and sales. "
				                + "It supports CRUD operations, role-based access control using Spring Security, Swagger documentation, "
				                + "pagination, and optional Thymeleaf-based HTML views. Only authenticated users with the ADMIN role "
				                + "can perform create, update, or delete operations."))
				.addSecurityItem(new SecurityRequirement().addList("basicAuth"))
				.components(new Components().addSecuritySchemes("basicAuth",
						new SecurityScheme().name("basicAuth").type(SecurityScheme.Type.HTTP).scheme("basic")));
	}
}

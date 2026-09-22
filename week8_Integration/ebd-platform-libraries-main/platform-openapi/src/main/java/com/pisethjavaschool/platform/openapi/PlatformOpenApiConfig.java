package com.pisethjavaschool.platform.openapi;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.*;

@Configuration
public class PlatformOpenApiConfig {
	@Bean
	public OpenAPI platformOpenApi() {
		return new OpenAPI().components(new Components().addSecuritySchemes("bearerAuth",
				new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
	}
}

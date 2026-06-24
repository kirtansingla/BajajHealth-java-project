package com.example.bfhl.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("BFHL REST Service")
                        .version("1.0.0")
                        .description("Production-ready Java Spring Boot REST API that processes mixed alphanumeric list payloads and performs custom categorization, calculations, and string reversals.")
                        .contact(new Contact()
                                .name("API Developer")
                                .email("support@example.com")));
    }
}

package com.agilysys.calculator.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI configuration for Swagger documentation.
 */
@Configuration
public class OpenApiConfig {
    
    @Bean
    public OpenAPI calculatorOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Calculator API")
                .description("RESTful API for performing basic arithmetic operations")
                .version("1.0.0")
                .contact(new Contact()
                    .name("Agilysys Development Team")
                    .email("dev@agilysys.com"))
                .license(new License()
                    .name("Apache 2.0")
                    .url("https://www.apache.org/licenses/LICENSE-2.0.html")));
    }
}

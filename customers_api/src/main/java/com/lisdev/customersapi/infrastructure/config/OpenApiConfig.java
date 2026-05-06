package com.lisdev.customersapi.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Customers API")
                        .description(
                                "Customer management: create, update, delete, id lookup by identification, "
                                        + "Reactive WebFlux; base path `/api/v1/customers`.")
                        .version("0.0.1"))
                .addServersItem(new Server().url("/").description("Current server"));
    }
}

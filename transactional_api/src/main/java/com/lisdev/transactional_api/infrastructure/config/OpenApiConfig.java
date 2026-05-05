package com.lisdev.transactional_api.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Transactional API")
                        .description(
                                "Account management, movements (deposits and withdrawals), and period reports. "
                                        + "Reactive API; base path `/api/v1`.")
                        .version("1.0.0"))
                .addServersItem(new Server().url("/").description("Current server"));
    }
}

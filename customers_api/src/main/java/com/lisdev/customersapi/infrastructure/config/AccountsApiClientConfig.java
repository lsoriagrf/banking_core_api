package com.lisdev.customersapi.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AccountsApiClientConfig {

    @Bean(name = "accountsWebClient")
    WebClient accountsWebClient(AccountsApiProperties properties) {
        return WebClient.builder()
                .baseUrl(properties.getBaseUrl())
                .build();
    }

}

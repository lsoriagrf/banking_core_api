package com.lisdev.customers_api.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.transactional-api")
public class AccountsApiProperties {

    private String baseUrl;

}

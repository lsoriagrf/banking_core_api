package com.lisdev.transactionalapi.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.customers-api")
public class CustomersApiProperties {
	
	private String baseUrl;
	
}

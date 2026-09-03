package com.lisdev.account.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.customer-api")
public class CustomersApiProperties {

	private String baseUrl;
	private String path;

}

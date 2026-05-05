package com.lisdev.customers_api.infrastructure.accounts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AccountsExistsResponse(@JsonProperty("exists") Boolean exists) {}

package com.lisdev.account.infrastructure.customers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
record CustomerApiDto(Integer id) {}

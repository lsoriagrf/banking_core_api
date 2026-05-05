package com.lisdev.transactional_api.infrastructure.customers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
record CustomerApiDto(Integer id) {}

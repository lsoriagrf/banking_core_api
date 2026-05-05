package com.lisdev.customers_api.infrastructure.web.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCustomer extends Person {

    @NotNull(message = "Customer id is required")
    @Positive(message = "Customer id must be a positive number")
    private Integer id;

}

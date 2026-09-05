package com.lisdev.account.infrastructure.web.dto.request;

import java.math.BigDecimal;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateAccount {

    @NotNull(message = "Customer id is required")
    private Integer customerId;

    @NotNull(message = "Account type id is required")
    private Integer accountTypeId;

    @NotNull(message = "Initial balance is required (can be zero)")
    @DecimalMin(value = "0.0", inclusive = true, message = "Balance cannot be negative")
    private BigDecimal balance;

}

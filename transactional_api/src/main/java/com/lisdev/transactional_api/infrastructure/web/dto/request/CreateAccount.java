package com.lisdev.transactional_api.infrastructure.web.dto.request;

import java.math.BigDecimal;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
public class CreateAccount extends FindByIdentification {

    @NotNull(message = "Account type id is required")
    private Integer accountTypeId;

    @NotNull(message = "Initial balance is required (can be zero)")
    @DecimalMin(value = "0.0", inclusive = true, message = "Balance cannot be negative")
    private BigDecimal balance;

}

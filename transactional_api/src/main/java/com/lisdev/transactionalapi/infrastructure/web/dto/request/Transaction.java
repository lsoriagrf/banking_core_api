package com.lisdev.transactionalapi.infrastructure.web.dto.request;

import java.math.BigDecimal;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
public class Transaction extends FindByIdentification {

    @NotBlank(message = "Account number is required")
    @Pattern(regexp = "\\d{10}", message = "Account number must be 10 digits")
    private String accountNumber;

    @NotNull(message = "The amount is required")
    @Positive(message = "Amount must be positive")
    @Digits(integer = 12, fraction = 2, message = "Only up to two decimal places are allowed")
    private BigDecimal amount;

}

package com.lisdev.transactionalapi.application.dto.response;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountResponse {

    private Integer id;
    private Integer customerId;
    private String accountNumber;
    private Integer accountTypeId;
    private String accountType;
    private BigDecimal balance;
    private Boolean status;
}

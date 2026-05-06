package com.lisdev.transactionalapi.infrastructure.web.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransactionResponse {

    private String transactionCode;
    private String status;
    private BigDecimal amount;
    private LocalDateTime date;
}

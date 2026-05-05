package com.lisdev.transactional_api.application.exception;

import java.math.BigDecimal;

public class InsufficientFundsException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InsufficientFundsException(String accountNumber, BigDecimal balance, BigDecimal withdrawalAmount) {
        super(String.format("Insufficient funds in account: %s. Balance: %s, debit attempt: %s",
                accountNumber, balance, withdrawalAmount));
    }
}

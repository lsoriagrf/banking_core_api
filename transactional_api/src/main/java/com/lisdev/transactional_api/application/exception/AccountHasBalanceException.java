package com.lisdev.transactional_api.application.exception;

import com.lisdev.transactional_api.application.Messages;

public class AccountHasBalanceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AccountHasBalanceException() {
        super(Messages.ACCOUNT_HAS_BALANCE);
    }
}

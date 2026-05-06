package com.lisdev.transactionalapi.domain.exception;

import com.lisdev.transactionalapi.domain.Messages;

public class AccountHasBalanceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AccountHasBalanceException() {
        super(Messages.ACCOUNT_HAS_BALANCE);
    }
}

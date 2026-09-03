package com.lisdev.account.domain.exception;

import com.lisdev.account.domain.Messages;

public class AccountHasBalanceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AccountHasBalanceException() {
        super(Messages.ACCOUNT_HAS_BALANCE);
    }
}

package com.lisdev.transactionalapi.domain.exception;

import com.lisdev.transactionalapi.domain.Messages;

public class AccountNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AccountNotFoundException() {
        super(Messages.ACCOUNT_NOT_FOUND);
    }
}

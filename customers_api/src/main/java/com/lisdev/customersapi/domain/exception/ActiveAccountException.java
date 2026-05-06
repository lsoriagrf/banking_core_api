package com.lisdev.customersapi.domain.exception;

import com.lisdev.customersapi.domain.Messages;

public class ActiveAccountException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ActiveAccountException() {
        super(Messages.ACTIVE_ACCOUNT);
    }

}

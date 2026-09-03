package com.lisdev.customer.domain.exception;

import com.lisdev.customer.domain.Messages;

public class ActiveAccountException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ActiveAccountException() {
        super(Messages.ACTIVE_ACCOUNT);
    }

}

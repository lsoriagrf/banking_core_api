package com.lisdev.customer.domain.exception;

import com.lisdev.customer.domain.Messages;

public class CustomerAlreadyActiveException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CustomerAlreadyActiveException() {
        super(Messages.CUSTOMER_ALREADY_ACTIVE);
    }

}

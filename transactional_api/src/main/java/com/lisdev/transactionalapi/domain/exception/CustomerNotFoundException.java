package com.lisdev.transactionalapi.domain.exception;

import com.lisdev.transactionalapi.domain.Messages;

public class CustomerNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CustomerNotFoundException(Integer customerId) {
        super(Messages.CUSTOMER_NOT_FOUND + customerId);
    }

    public CustomerNotFoundException(String identification) {
        super(Messages.CUSTOMER_NOT_FOUND + identification);
    }
}

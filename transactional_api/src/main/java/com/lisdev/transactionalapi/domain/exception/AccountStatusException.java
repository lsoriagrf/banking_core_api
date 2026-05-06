package com.lisdev.transactionalapi.domain.exception;

import com.lisdev.transactionalapi.domain.Messages;

public class AccountStatusException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public static AccountStatusException redundantWithRequestedState(Boolean requestedStatus) {
        String message = Boolean.TRUE.equals(requestedStatus)
                        ? Messages.ACCOUNT_ALREADY_ACTIVE
                        : Messages.ACCOUNT_ALREADY_INACTIVE;
        return new AccountStatusException(message);
    }

    private AccountStatusException(String message) {
        super(message);
    }
}

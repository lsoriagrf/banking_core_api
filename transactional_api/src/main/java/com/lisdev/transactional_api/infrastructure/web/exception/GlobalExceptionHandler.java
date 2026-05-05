package com.lisdev.transactional_api.infrastructure.web.exception;

import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.resource.NoResourceFoundException;
import com.lisdev.transactional_api.application.Messages;
import com.lisdev.transactional_api.application.exception.AccountHasBalanceException;
import com.lisdev.transactional_api.application.exception.AccountNotFoundException;
import com.lisdev.transactional_api.application.exception.AccountStatusException;
import com.lisdev.transactional_api.application.exception.CustomerNotFoundException;
import com.lisdev.transactional_api.application.exception.InsufficientFundsException;
import com.lisdev.transactional_api.domain.exception.InvalidReportPeriodException;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccountNotFoundException.class)
    public ProblemDetail handleAccountNotFoundException(AccountNotFoundException ex) {
        log.warn("[{}] {}", GlobalExceptionHandler.class.getSimpleName(), ex.getMessage());
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(AccountStatusException.class)
    public ProblemDetail handleAccountStatusException(AccountStatusException ex) {
        log.warn("[{}] {}", GlobalExceptionHandler.class.getSimpleName(), ex.getMessage());
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(AccountHasBalanceException.class)
    public ProblemDetail handleAccountHasBalanceException(AccountHasBalanceException ex) {
        log.warn("[{}] {}", GlobalExceptionHandler.class.getSimpleName(), ex.getMessage());
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ProblemDetail handleInsufficientFundsException(InsufficientFundsException ex) {
        log.warn("[{}] {}", GlobalExceptionHandler.class.getSimpleName(), ex.getMessage());
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ProblemDetail handleCustomerNotFoundException(CustomerNotFoundException ex) {
        log.error("[{}] {}", GlobalExceptionHandler.class.getSimpleName(), ex.getMessage(), ex);
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(InvalidReportPeriodException.class)
    public ProblemDetail handleInvalidReportPeriodException(InvalidReportPeriodException ex) {
        log.warn("[{}] {}", GlobalExceptionHandler.class.getSimpleName(), ex.getMessage());
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(WebClientRequestException.class)
    public ProblemDetail handleWebClientRequestException(WebClientRequestException ex) {
        log.error("[{}] {}", GlobalExceptionHandler.class.getSimpleName(), ex.getMessage(), ex);
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,
                Messages.DOWNSTREAM_SERVICE_NO_RESPONSE);
    }
    
    @ExceptionHandler(NoResourceFoundException.class)
    public ProblemDetail handleNoResourceFoundException(NoResourceFoundException ex) {
        log.warn("[{}] {}", GlobalExceptionHandler.class.getSimpleName(), ex.getMessage());
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, Messages.PATH_NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        log.error("[{}] {}", GlobalExceptionHandler.class.getSimpleName(), ex.getMessage(), ex);
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, Messages.DB_ERROR);
    }
    
    @ExceptionHandler(WebExchangeBindException.class)
    public ProblemDetail handleWebExchangeBindException(WebExchangeBindException ex) {
        log.warn("[{}] {}", GlobalExceptionHandler.class.getSimpleName(), ex.getMessage());
        String detail = ex.getBindingResult().getAllErrors().stream()
                .findFirst()
                .map(err -> err instanceof FieldError fe
                        ? Objects.toString(fe.getDefaultMessage(), "")
                        : Objects.toString(err.getDefaultMessage(), ""))
                .filter(s -> !s.isBlank())
                .orElse(Messages.INVALID_REQUEST);
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, detail);
    }

    @ExceptionHandler(DataAccessResourceFailureException.class)
    public ProblemDetail handleDataAccessResourceFailureException(DataAccessResourceFailureException ex) {
        log.error("[{}] {}", GlobalExceptionHandler.class.getSimpleName(), ex.getMessage(), ex);
        return ProblemDetail.forStatusAndDetail(HttpStatus.SERVICE_UNAVAILABLE, Messages.DB_NOT_AVAILABLE);
    }

    @ExceptionHandler(TransientDataAccessResourceException.class)
    public ProblemDetail handleTransientDataAccessResourceException(TransientDataAccessResourceException ex) {
        log.error("[{}] {}", GlobalExceptionHandler.class.getSimpleName(), ex.getMessage(), ex);
        return ProblemDetail.forStatusAndDetail(HttpStatus.SERVICE_UNAVAILABLE, Messages.DB_NOT_AVAILABLE);
    }

}

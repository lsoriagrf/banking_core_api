package com.lisdev.transactionalapi.application.service.report;

import com.lisdev.transactionalapi.domain.Messages;
import com.lisdev.transactionalapi.domain.exception.CustomerNotFoundException;
import com.lisdev.transactionalapi.application.port.in.TransactionReportPortIn;
import com.lisdev.transactionalapi.application.port.out.AccountPersistencePort;
import com.lisdev.transactionalapi.application.port.out.CustomerPort;
import com.lisdev.transactionalapi.application.port.out.MovementPersistencePort;
import com.lisdev.transactionalapi.common.UseCase;
import com.lisdev.transactionalapi.domain.model.Account;
import com.lisdev.transactionalapi.domain.model.Movement;
import com.lisdev.transactionalapi.domain.model.report.MovementReport;
import com.lisdev.transactionalapi.domain.model.report.MovementReportBuilder;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class MovementReportService implements TransactionReportPortIn {

    private final AccountPersistencePort accountPersistencePort;
    private final MovementPersistencePort movementPersistencePort;
    private final CustomerPort customerPort;

    @Override
    public Mono<MovementReport> getReport(Integer customerId, LocalDate startDate, LocalDate endDate) {
        log.info("{}getReport {}", Messages.START, reportParams(customerId, startDate, endDate));
        MovementReportBuilder.validateDateRange(startDate, endDate);
        Mono<MovementReport> reportMono = customerPort
                .resolveCustomerIdentityById(customerId)
                .flatMap(identity -> identity.isPresent()
                        ? assembleReport(customerId, identity.identification(), identity.fullName(), startDate, endDate)
                        : Mono.error(new CustomerNotFoundException(customerId)));
        return reportMono.doOnSuccess(r -> log.info("{}getReport {}", Messages.END, reportDone(customerId)));
    }

    private Mono<MovementReport> assembleReport(
            Integer customerId,
            String customerIdentification,
            String fullName,
            LocalDate startDate,
            LocalDate endDate) {
        Mono<List<Account>> accountsMono = accountPersistencePort
                .findAccountsByCustomerId(customerId)
                .filter(acc -> Boolean.TRUE.equals(acc.getStatus()))
                .collectList();
        Mono<List<Movement>> movementsMono = movementPersistencePort
                .findByCustomerIdAndCreatedAtBetween(customerId, startDate, endDate)
                .collectList();
        return Mono.zip(accountsMono, movementsMono)
                .map(tuple -> MovementReportBuilder.buildMovementReport(
                        customerIdentification,
                        fullName,
                        startDate,
                        endDate,
                        tuple.getT1(),
                        tuple.getT2()));
    }

    private static String reportParams(Integer customerId, LocalDate startDate, LocalDate endDate) {
        return String.format("customerId=%s, period=%s..%s", customerId, startDate, endDate);
    }

    private static String reportDone(Integer clientId) {
        return "customerId=" + clientId;
    }
}

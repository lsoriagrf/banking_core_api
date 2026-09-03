package com.lisdev.account.application.port.in;

import com.lisdev.account.domain.model.report.MovementReport;
import reactor.core.publisher.Mono;
import java.time.LocalDate;

public interface TransactionReportPortIn {

    Mono<MovementReport> getReport(Integer clientId, LocalDate startDate, LocalDate endDate);

}

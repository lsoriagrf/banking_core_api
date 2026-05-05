package com.lisdev.transactional_api.application.port.in;

import com.lisdev.transactional_api.domain.model.report.MovementReport;
import reactor.core.publisher.Mono;
import java.time.LocalDate;

public interface TransactionReportPortIn {

    Mono<MovementReport> getReport(Integer clientId, LocalDate startDate, LocalDate endDate);

}

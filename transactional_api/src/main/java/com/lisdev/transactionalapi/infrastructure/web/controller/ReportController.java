package com.lisdev.transactionalapi.infrastructure.web.controller;

import com.lisdev.transactionalapi.application.port.in.TransactionReportPortIn;
import com.lisdev.transactionalapi.common.WebAdapter;
import com.lisdev.transactionalapi.infrastructure.web.dto.response.TransactionReportResponse;
import com.lisdev.transactionalapi.infrastructure.web.mapper.ReportWebMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import java.time.LocalDate;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reports")
public class ReportController {

    private final TransactionReportPortIn transactionReportPortIn;
    private final ReportWebMapper reportWebMapper;

    @GetMapping(path = "/{client-id}")
    public Mono<TransactionReportResponse> getReport(
            @PathVariable("client-id") Integer clientId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return transactionReportPortIn
                .getReport(clientId, startDate, endDate)
                .map(reportWebMapper::toTransactionReportResponse);
    }
}

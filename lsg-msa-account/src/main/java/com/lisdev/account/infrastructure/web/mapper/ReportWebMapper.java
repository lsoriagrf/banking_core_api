package com.lisdev.account.infrastructure.web.mapper;

import com.lisdev.account.domain.model.report.MovementReport;
import com.lisdev.account.infrastructure.web.dto.response.TransactionReportResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReportWebMapper {

    default TransactionReportResponse toTransactionReportResponse(MovementReport domain) {
        TransactionReportResponse response = new TransactionReportResponse();
        response.setData(mapToData(domain));
        return response;
    }

    TransactionReportResponse.Data mapToData(MovementReport domain);
}

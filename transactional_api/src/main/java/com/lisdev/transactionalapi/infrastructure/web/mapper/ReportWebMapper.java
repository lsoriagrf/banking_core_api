package com.lisdev.transactionalapi.infrastructure.web.mapper;

import com.lisdev.transactionalapi.domain.model.report.MovementReport;
import com.lisdev.transactionalapi.infrastructure.web.dto.response.TransactionReportResponse;
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

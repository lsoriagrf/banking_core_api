package com.lisdev.transactional_api.infrastructure.web.mapper;

import com.lisdev.transactional_api.domain.model.report.MovementReport;
import com.lisdev.transactional_api.infrastructure.web.dto.response.TransactionReportResponse;
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

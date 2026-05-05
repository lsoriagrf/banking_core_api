package com.lisdev.transactional_api.infrastructure.web.mapper;

import com.lisdev.transactional_api.application.port.in.command.TransactionCommand;
import com.lisdev.transactional_api.domain.model.Movement;
import com.lisdev.transactional_api.infrastructure.web.dto.request.Transaction;
import com.lisdev.transactional_api.infrastructure.web.dto.response.TransactionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MovementWebMapper {

    TransactionCommand toTransactionCommand(Transaction dto);

    @Mapping(target = "status", constant = "OK")
    @Mapping(target = "transactionCode", source = "transactionCode")
    @Mapping(target = "date", source = "createdAt")
    TransactionResponse toTransactionResponse(Movement movement);
}

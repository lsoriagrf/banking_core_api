package com.lisdev.transactionalapi.infrastructure.web.mapper;

import com.lisdev.transactionalapi.application.port.in.command.TransactionCommand;
import com.lisdev.transactionalapi.domain.model.Movement;
import com.lisdev.transactionalapi.infrastructure.web.dto.request.Transaction;
import com.lisdev.transactionalapi.infrastructure.web.dto.response.TransactionResponse;
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

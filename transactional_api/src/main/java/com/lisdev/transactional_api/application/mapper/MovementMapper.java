package com.lisdev.transactional_api.application.mapper;

import com.lisdev.transactional_api.application.port.in.command.TransactionCommand;
import com.lisdev.transactional_api.domain.model.Movement;
import java.math.BigDecimal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MovementMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "accountId", source = "accountId")
    @Mapping(target = "transactionTypeId", source = "transactionTypeId")
    @Mapping(target = "transactionCode", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(target = "amount", source = "body.amount")
    @Mapping(target = "balance", source = "newBalance")
    @Mapping(target = "note", source = "transactionType")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "createdBy", source = "body.identification")
    Movement toNewMovement(
            TransactionCommand body,
            Integer accountId,
            BigDecimal newBalance,
            Integer transactionTypeId,
            String transactionType);
}

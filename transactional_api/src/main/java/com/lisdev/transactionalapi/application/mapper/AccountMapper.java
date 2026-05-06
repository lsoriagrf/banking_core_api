package com.lisdev.transactionalapi.application.mapper;

import com.lisdev.transactionalapi.application.dto.response.AccountResponse;
import com.lisdev.transactionalapi.application.port.in.command.CreateAccountCommand;
import com.lisdev.transactionalapi.domain.model.Account;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {

    @Mapping(target = "status", constant = "true")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "createdBy", source = "body.identification")
    @Mapping(target = "customerId", source = "customerId")
    @Mapping(
            target = "accountNumber",
            expression = "java(com.lisdev.transactionalapi.domain.model.Account.generateAccountNumber())")
    Account toCreateAccount(CreateAccountCommand body, Integer customerId);

    AccountResponse toAccountResponse(Account account);

    List<AccountResponse> toAccountResponseList(List<Account> accounts);
}

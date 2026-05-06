package com.lisdev.transactionalapi.infrastructure.web.mapper;

import com.lisdev.transactionalapi.application.dto.response.AccountResponse;
import com.lisdev.transactionalapi.application.port.in.command.CreateAccountCommand;
import com.lisdev.transactionalapi.domain.model.Account;
import com.lisdev.transactionalapi.infrastructure.web.dto.request.CreateAccount;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class AccountWebMapper {

    public abstract CreateAccountCommand toCreateCommand(CreateAccount dto);

    public abstract AccountResponse toResponse(Account account);
}

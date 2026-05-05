package com.lisdev.transactional_api.infrastructure.web.mapper;

import com.lisdev.transactional_api.application.dto.response.AccountResponse;
import com.lisdev.transactional_api.application.port.in.command.CreateAccountCommand;
import com.lisdev.transactional_api.domain.model.Account;
import com.lisdev.transactional_api.infrastructure.web.dto.request.CreateAccount;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class AccountWebMapper {

    public abstract CreateAccountCommand toCreateCommand(CreateAccount dto);

    public abstract AccountResponse toResponse(Account account);
}

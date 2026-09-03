package com.lisdev.account.application.mapper;

import com.lisdev.account.application.port.in.command.TransactionCommand;
import com.lisdev.account.domain.model.Movement;
import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class MovementMapper {

    public Movement toNewMovement(
            TransactionCommand body,
            Integer accountId,
            BigDecimal newBalance,
            Integer transactionTypeId,
            String transactionType) {
        return Movement.createNew(
                accountId,
                transactionTypeId,
                UUID.randomUUID(),
                body.getAmount(),
                newBalance,
                transactionType,
                body.getIdentification());
    }
}

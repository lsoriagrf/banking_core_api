package com.lisdev.transactionalapi.domain.model;

import lombok.Getter;
import java.util.Arrays;

@Getter
public enum MovementType {

    Deposit(1, "Deposito"),
    Withdrawal(2, "Retiro");

    private final Integer id;
    private final String description;

    MovementType(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    public static String descriptionById(Integer id) {
        if (id == null) {
            return null;
        }
        return Arrays.stream(values())
                .filter(a -> id.equals(a.getId()))
                .map(MovementType::getDescription)
                .findFirst()
                .orElse("Unknown");
    }
}

package com.lisdev.transactional_api.domain.model;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum AccountType {

    checkingacc(1, "Corriente"),
    savingsacc(2, "Ahorros");

    private final Integer id;
    private final String description;

    AccountType(Integer id, String descripcion) {
        this.id = id;
        this.description = descripcion;
    }

    public static String descriptionById(Integer id) {
        if (id == null) {
            return null;
        }
        return Arrays.stream(values())
                .filter(a -> id.equals(a.getId()))
                .map(AccountType::getDescription)
                .findFirst()
                .orElse("Unknown");
    }
}

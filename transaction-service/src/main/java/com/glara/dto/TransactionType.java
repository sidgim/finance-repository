package com.glara.dto;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TransactionType {
    INCOME("income"),
    EXPENSE("expense");

    private final String valor;

    TransactionType(String valor) {
        this.valor = valor;
    }

    @JsonValue
    @Override
    public String toString() {
        return valor;
    }

    public static TransactionType fromString(String valor) {
        for (TransactionType t : values()) {
            if (t.valor.equalsIgnoreCase(valor)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Tipo inválido: " + valor);
    }
}


package com.glara.dto;

public enum TransactionType {
    INGRESO("ingreso"),
    GASTO("gasto");

    private final String valor;

    TransactionType(String valor) {
        this.valor = valor;
    }

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

package com.glara.dto;



import io.vertx.mutiny.sqlclient.Row;

import java.time.OffsetDateTime;
import java.util.UUID;

public record TransactionDTO(UUID id, String description, UUID accountId, UUID subcategoryId, TransactionType type, Double amount, OffsetDateTime transactionDate) {

    public static TransactionDTO fromRow(Row row) {
        return new TransactionDTO(
                row.getUUID("id"),
                row.getString("description"),
                row.getUUID("account_id"),
                row.getUUID("subcategory_id"),
                TransactionType.fromString(row.getString("type")),
                row.getDouble("amount"),
                row.getOffsetDateTime("transaction_date")
        );
    }
}

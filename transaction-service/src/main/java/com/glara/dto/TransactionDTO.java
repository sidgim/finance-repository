package com.glara.dto;



import io.vertx.mutiny.sqlclient.Row;

import java.time.OffsetDateTime;
import java.util.UUID;

public record TransactionDTO(UUID id, String description, UUID accountId, UUID subcategoryId,UUID vendorId ,TransactionType type, Double amount, OffsetDateTime createdAt) {

    public static TransactionDTO fromRow(Row row) {
        return new TransactionDTO(
                row.getUUID("id"),
                row.getString("description"),
                row.getUUID("account_id"),
                row.getUUID("subcategory_id"),
                row.getUUID("vendor_id"),
                TransactionType.fromString(row.getString("transaction_type")),
                row.getDouble("amount"),
                row.getOffsetDateTime("created_at")
        );
    }
}

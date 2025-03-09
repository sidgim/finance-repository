package com.glara.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import io.vertx.mutiny.sqlclient.Row;

import java.util.UUID;

@RegisterForReflection
public record SubcategoryDTO(UUID id, String name, String description, UUID categoryId) {

    public static SubcategoryDTO fromRow(Row row) {
        return new SubcategoryDTO(
                row.getUUID("id"),
                row.getString("name"),
                row.getString("description"),
                row.getUUID("category_id")
        );
    }
}

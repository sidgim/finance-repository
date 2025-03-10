package com.glara.dto;

import io.vertx.mutiny.sqlclient.Row;

import java.util.UUID;

public record VendorDTO(UUID id , String name) {

    public static VendorDTO fromRow(Row row) {
        return new VendorDTO(
                row.getUUID("id"),
                row.getString("name")
        );
    }
}

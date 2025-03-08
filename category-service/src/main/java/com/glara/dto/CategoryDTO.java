package com.glara.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import io.vertx.mutiny.sqlclient.Row;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import java.util.UUID;

@Schema(description = "DTO de una categoría")
@RegisterForReflection
public record CategoryDTO(
        @Schema(description = "ID de la categoría", example = "f47ac10b-58cc-4372-a567-0e02b2c3d479")
        UUID id,

        @Schema(description = "Nombre de la categoría", example = "Electrónica")
        String name,

        @Schema(description = "Descripción de la categoría", example = "Productos electrónicos y gadgets")
        String description
) {

        public static CategoryDTO fromRow(Row row) {
                return new CategoryDTO(
                        row.getUUID("id"),
                        row.getString("name"),
                        row.getString("description")
                );
        }
}


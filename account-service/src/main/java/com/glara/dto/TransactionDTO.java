package com.glara.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record TransactionDTO(UUID id, String description, UUID accountId, UUID subcategoryId, UUID vendorId , String type, Double amount, OffsetDateTime createdAt) {
}

package com.glara.dto;

import java.math.BigDecimal;


public record AccountDTO(
        String id,
        String name,
        BigDecimal currentBalance,
        String accountTypeId,
        String userId
) {}



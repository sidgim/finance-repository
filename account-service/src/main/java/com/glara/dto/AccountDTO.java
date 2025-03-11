package com.glara.dto;

import java.math.BigDecimal;
import java.util.List;


public record AccountDTO(
        String id,
        String name,
        BigDecimal currentBalance,
        String accountTypeId,
        String userId,
        List<TransactionDTO> transactions
) {}


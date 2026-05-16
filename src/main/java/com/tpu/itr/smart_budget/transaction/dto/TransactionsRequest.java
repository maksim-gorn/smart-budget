package com.tpu.itr.smart_budget.transaction.dto;

import java.time.LocalDate;

public record TransactionsRequest(
        String apiKey,
        LocalDate startDate,
        LocalDate endDate
) {
}

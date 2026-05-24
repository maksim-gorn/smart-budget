package com.tpu.itr.smart_budget.budget.api;

import java.time.LocalDate;

public record ImportedTransaction(
        long bankTransactionId,
        String merchant,
        int mcc,
        float amount,
        String currency,
        LocalDate date
) {
}

package com.tpu.itr.pseudo_api.transactions.dto;

import java.time.LocalDate;

public record TransactionsRequest(
        String apiKey,
        LocalDate startDate,
        LocalDate endDate
) {
}

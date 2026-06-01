package com.tpu.itr.smart_budget.transaction.webClient.dto;

import java.util.List;

public record TransactionsResponse(
        int total,
        List<Transaction> transactions
)
{
}

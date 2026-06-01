package com.tpu.itr.pseudo_api.transactions.dto;

import java.util.List;

public record TransactionsResponse(
        int total,
        List<Transaction> transactions
)
{
}

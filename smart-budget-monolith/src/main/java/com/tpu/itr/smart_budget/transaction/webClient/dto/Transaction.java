package com.tpu.itr.smart_budget.transaction.webClient.dto;


import com.tpu.itr.smart_budget.transaction.webClient.utils.Currencies;

import java.time.LocalDate;

public record Transaction(
        long id,
        String merchant,
        int mcc,
        float amount,
        Currencies currency,
        LocalDate date
) {}

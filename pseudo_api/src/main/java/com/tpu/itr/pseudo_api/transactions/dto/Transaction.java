package com.tpu.itr.pseudo_api.transactions.dto;

import com.tpu.itr.pseudo_api.transactions.utils.Currencies;

import java.time.LocalDate;

public record Transaction(
        long id,
        String merchant,
        int mcc,
        float amount,
        Currencies currency,
        LocalDate date
) {}

package com.tpu.itr.pseudo_api.transactions.data;

import com.tpu.itr.pseudo_api.transactions.dto.Transaction;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository {

    List<Transaction> findAllByApiKey(String apiKey);

    List<Transaction> findAll();

    List<Transaction> findByApiKeyAndDateRange(
            String apiKey,
            LocalDate from,
            LocalDate to
    );

    void save(Transaction transaction);
}
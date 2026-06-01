package com.tpu.itr.pseudo_api.transactions;

import com.tpu.itr.pseudo_api.transactions.data.InMemoryTransactionRepository;
import com.tpu.itr.pseudo_api.transactions.data.TransactionRepository;
import com.tpu.itr.pseudo_api.transactions.dto.Transaction;
import com.tpu.itr.pseudo_api.transactions.dto.TransactionsRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionsService {
    private TransactionRepository transactionRepository;

    private static final Logger log = LoggerFactory.getLogger(TransactionsService.class);

    public TransactionsService(TransactionRepository transactionRepository) {
        this.transactionRepository = new InMemoryTransactionRepository();
    }


    public List<Transaction> getTransactions(TransactionsRequest request) {

        String apiKey = request.apiKey();
        LocalDate start = request.startDate();
        LocalDate end = request.endDate();


        if (start.isAfter(end)) {
            throw new IllegalArgumentException("startDate must be before endDate");
        }

        List<Transaction> transactions = transactionRepository.findByApiKeyAndDateRange(apiKey, start, end);

        log.info("got transactions: " + transactions.size());
        return transactions;

    }
}

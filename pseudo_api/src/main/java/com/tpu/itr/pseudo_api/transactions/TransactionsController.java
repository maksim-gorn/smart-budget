package com.tpu.itr.pseudo_api.transactions;


import com.tpu.itr.pseudo_api.transactions.dto.Transaction;
import com.tpu.itr.pseudo_api.transactions.dto.TransactionsRequest;
import com.tpu.itr.pseudo_api.transactions.dto.TransactionsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TransactionsController {

    public TransactionsController(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    private final TransactionsService transactionsService;

    @PostMapping("/gettransactions")
    public ResponseEntity<TransactionsResponse> getTransactions(@RequestBody TransactionsRequest request) {
        List<Transaction> transactions = transactionsService.getTransactions(request);
        return ResponseEntity.ok(new TransactionsResponse(transactions.size(), transactions));
    }
}

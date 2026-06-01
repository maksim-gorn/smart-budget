package com.tpu.itr.smart_budget.transaction.webClient;


import com.tpu.itr.smart_budget.transaction.webClient.dto.TransactionsRequest;
import com.tpu.itr.smart_budget.transaction.webClient.dto.TransactionsResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
public class ApiService {
    private final WebClient webClient;

    public ApiService(WebClient webClient) {
        this.webClient = webClient;
    }

    public TransactionsResponse getTransactions(final String key, LocalDate start, LocalDate end)
    {
        TransactionsRequest transactionsRequest = new TransactionsRequest(key, start, end);
        return webClient
                .post()
                .uri("/api/gettransactions")
                .bodyValue(transactionsRequest)
                .retrieve()
                .bodyToMono(TransactionsResponse.class)
                .block();
    }

}

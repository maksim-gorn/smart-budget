package com.tpu.itr.smart_budget.transaction;

import com.tpu.itr.smart_budget.transaction.dto.AddApiRequest;
import com.tpu.itr.smart_budget.transaction.usersApi.UserApiKeyEntity;
import com.tpu.itr.smart_budget.transaction.usersApi.UserApiKeyRepository;
import com.tpu.itr.smart_budget.transaction.webClient.ApiService;
import com.tpu.itr.smart_budget.transaction.webClient.dto.TransactionsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class TransactionService {
    private final UserApiKeyRepository userApiKeyRepository;
    private final ApiService apiService;
    private final com.tpu.itr.smart_budget.budget.TransactionService budgetTransactionService;

    private static final Logger log = LoggerFactory.getLogger(TransactionService.class);

    public TransactionService(
            UserApiKeyRepository userApiKeyRepository,
            ApiService apiService,
            com.tpu.itr.smart_budget.budget.TransactionService budgetTransactionService
    ) {
        this.userApiKeyRepository = userApiKeyRepository;
        this.apiService = apiService;
        this.budgetTransactionService = budgetTransactionService;
    }


    public void addApi(Long userId, AddApiRequest request) {

        UserApiKeyEntity entity =
                new UserApiKeyEntity(userId, request.apiKey());
        userApiKeyRepository.save(entity);
    }

    //тестовый метод, потом убрать
    public void update(Long userId){
        UserApiKeyEntity userApiKeyEntity = userApiKeyRepository.getReferenceById(userId);
        String key = userApiKeyEntity.getApiKey();
        TransactionsResponse transactionsResponse = apiService.getTransactions(key,
                userApiKeyEntity.getLast_update().toLocalDate().minusDays(1),
                LocalDate.now());

        budgetTransactionService.addTransactions(userId, transactionsResponse.transactions());

        log.info("Fetched "+transactionsResponse.total()+" transctions");
        log.info("Fetched transactions from: "+
                userApiKeyEntity.getLast_update().toLocalDate().minusDays(1)
                + " until: "+ LocalDate.now());

        userApiKeyEntity.setLast_update(LocalDateTime.now());
        userApiKeyRepository.save(userApiKeyEntity);
    }
}


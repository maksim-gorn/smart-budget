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

@Service
public class TransactionService {
    private final UserApiKeyRepository userApiKeyRepository;
    private final ApiService apiService;

    private static final Logger log = LoggerFactory.getLogger(TransactionService.class);

    public TransactionService(
            UserApiKeyRepository userApiKeyRepository,
            ApiService apiService
    ) {
        this.userApiKeyRepository = userApiKeyRepository;
        this.apiService = apiService;
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
        apiService.getTransactions(key, LocalDate.now().minusDays(10),
                LocalDate.now().plusDays(10))
                .subscribe(transactionsResponse ->
                {log.info("Got TransactionResponse containing "+ transactionsResponse.total()+" items");
                });
    }
}


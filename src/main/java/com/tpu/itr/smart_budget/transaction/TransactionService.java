package com.tpu.itr.smart_budget.transaction;

import com.tpu.itr.smart_budget.transaction.dto.AddApiRequest;
import com.tpu.itr.smart_budget.transaction.usersApi.UserApiKeyEntity;
import com.tpu.itr.smart_budget.transaction.usersApi.UserApiKeyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    private final UserApiKeyRepository userApiKeyRepository;

    private static final Logger log = LoggerFactory.getLogger(TransactionService.class);

    public TransactionService(
            UserApiKeyRepository userApiKeyRepository
    ) {
        this.userApiKeyRepository = userApiKeyRepository;
    }


    public void addApi(Long userId, AddApiRequest request) {

        UserApiKeyEntity entity =
                new UserApiKeyEntity(userId, request.apiKey());
        userApiKeyRepository.save(entity);
    }
}


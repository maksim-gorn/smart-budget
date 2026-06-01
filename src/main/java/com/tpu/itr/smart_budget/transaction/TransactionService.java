package com.tpu.itr.smart_budget.transaction;

import com.tpu.itr.smart_budget.budget.api.BudgetTransactionImportPort;
import com.tpu.itr.smart_budget.budget.api.ImportedTransaction;
import com.tpu.itr.smart_budget.transaction.dto.AddApiRequest;
import com.tpu.itr.smart_budget.transaction.usersApi.UserApiKeyEntity;
import com.tpu.itr.smart_budget.transaction.usersApi.UserApiKeyRepository;
import com.tpu.itr.smart_budget.transaction.webClient.ApiService;
import com.tpu.itr.smart_budget.transaction.webClient.dto.Transaction;
import com.tpu.itr.smart_budget.transaction.webClient.dto.TransactionsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {
    private final UserApiKeyRepository userApiKeyRepository;
    private final ApiService apiService;
    private final BudgetTransactionImportPort budgetTransactionImportPort;

    private static final Logger log = LoggerFactory.getLogger(TransactionService.class);

    public TransactionService(
            UserApiKeyRepository userApiKeyRepository,
            ApiService apiService,
            BudgetTransactionImportPort budgetTransactionImportPort
    ) {
        this.userApiKeyRepository = userApiKeyRepository;
        this.apiService = apiService;
        this.budgetTransactionImportPort = budgetTransactionImportPort;
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

        budgetTransactionImportPort.importTransactions(userId, toImportedTransactions(transactionsResponse.transactions()));

        log.info("Fetched "+transactionsResponse.total()+" transctions");
        log.info("Fetched transactions from: "+
                userApiKeyEntity.getLast_update().toLocalDate().minusDays(1)
                + " until: "+ LocalDate.now());

        userApiKeyEntity.setLast_update(LocalDateTime.now());
        userApiKeyRepository.save(userApiKeyEntity);
    }

    private List<ImportedTransaction> toImportedTransactions(List<Transaction> transactions) {
        if (transactions == null || transactions.isEmpty()) {
            return List.of();
        }

        return transactions.stream()
                .map(transaction -> new ImportedTransaction(
                        transaction.id(),
                        transaction.merchant(),
                        transaction.mcc(),
                        transaction.amount(),
                        transaction.currency().name(),
                        transaction.date()
                ))
                .toList();
    }
}

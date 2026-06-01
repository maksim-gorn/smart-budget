package com.tpu.itr.smart_budget.budget;

import com.tpu.itr.smart_budget.authentication.user.UserEntity;
import com.tpu.itr.smart_budget.authentication.user.UserRepository;
import com.tpu.itr.smart_budget.budget.api.BudgetTransactionImportPort;
import com.tpu.itr.smart_budget.budget.api.ImportedTransaction;
import com.tpu.itr.smart_budget.budget.repository.TransactionEntity;
import com.tpu.itr.smart_budget.budget.repository.TransactionRepository;
import com.tpu.itr.smart_budget.budget.utils.MccCategoryResolver;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BudgetTransactionImportService implements BudgetTransactionImportPort {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public BudgetTransactionImportService(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public void importTransactions(Long userId, List<ImportedTransaction> transactions) {
        if (transactions == null || transactions.isEmpty()) {
            return;
        }

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        //пропускаю если existsByUserAndBankTransactionId, возможно нужна будет другая логика позже
        List<TransactionEntity> newTransactions = transactions.stream()
                .filter(transaction -> !transactionRepository.existsByUserAndBankTransactionId(user, transaction.bankTransactionId()))
                .map(transaction -> new TransactionEntity(
                        transaction.bankTransactionId(),
                        transaction.merchant(),
                        MccCategoryResolver.resolveCategory(transaction.mcc()),
                        transaction.amount(),
                        transaction.date(),
                        transaction.currency(),
                        user
                ))
                .toList();

        transactionRepository.saveAll(newTransactions);
    }
}

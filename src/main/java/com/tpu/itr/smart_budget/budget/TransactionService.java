package com.tpu.itr.smart_budget.budget;

import com.tpu.itr.smart_budget.authentication.dto.UserEntity;
import com.tpu.itr.smart_budget.authentication.user.UserRepository;
import com.tpu.itr.smart_budget.budget.repository.TransactionEntity;
import com.tpu.itr.smart_budget.budget.repository.TransactionRepository;
import com.tpu.itr.smart_budget.transaction.utils.MccCategoryResolver;
import com.tpu.itr.smart_budget.transaction.webClient.dto.Transaction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("budgetTransactionService")
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void addTransactions(Long userId, List<Transaction> transactions) {
        if (transactions == null || transactions.isEmpty()) {
            return;
        }

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        //пропускаю если existsByUserAndBankTransactionId, возможно нужна будет другая логика позже
        List<TransactionEntity> newTransactions = transactions.stream()
                .filter(transaction -> !transactionRepository.existsByUserAndBankTransactionId(user, transaction.id()))
                .map(transaction -> new TransactionEntity(
                        transaction.id(),
                        transaction.merchant(),
                        MccCategoryResolver.resolveCategory(transaction.mcc()),
                        transaction.amount(),
                        transaction.date(),
                        transaction.currency().name(),
                        user
                ))
                .toList();

        transactionRepository.saveAll(newTransactions);
    }
}

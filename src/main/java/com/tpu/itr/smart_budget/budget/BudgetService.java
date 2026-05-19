package com.tpu.itr.smart_budget.budget;

import com.tpu.itr.smart_budget.authentication.dto.UserEntity;
import com.tpu.itr.smart_budget.authentication.user.UserRepository;
import com.tpu.itr.smart_budget.common.BadRequestException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BudgetService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public BudgetService(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    private UserEntity getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof Long) {
            return userRepository.findById((Long) principal)
                    .orElseThrow(() -> new RuntimeException("User not found"));
        }
        throw new RuntimeException("Invalid authentication principal");
    }

    @Transactional(readOnly = true)
    public GetSpendingsResponse getSpendings(LocalDate startDate, LocalDate endDate) {
        UserEntity currentUser = getCurrentUser();
        List<TransactionDTO> transactions = transactionRepository
                .findByUserAndDateBetween(currentUser, startDate, endDate)
                .stream()
                .map(TransactionDTO::new)
                .collect(Collectors.toList());
        return new GetSpendingsResponse(transactions);
    }

    @Transactional
    public TransactionDTO updateCategory(Long transactionId, String newCategory) {
        UserEntity currentUser = getCurrentUser();
        TransactionEntity transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new BadRequestException("Transaction not found"));

        if (transaction.getUser().getId() != currentUser.getId()) {
            throw new BadRequestException("Access denied to this transaction");
        }

        transaction.setCategory(newCategory);
        return new TransactionDTO(transactionRepository.save(transaction));
    }

    @Transactional
    public TransactionDTO addTransaction(AddTransactionRequest request) {
        UserEntity currentUser = getCurrentUser();

        TransactionEntity transaction = new TransactionEntity(
                request.getMerchant(),
                request.getCategory(),
                request.getAmount(),
                request.getDate(),
                currentUser
        );

        TransactionEntity saved = transactionRepository.save(transaction);
        return new TransactionDTO(saved);
    }
}
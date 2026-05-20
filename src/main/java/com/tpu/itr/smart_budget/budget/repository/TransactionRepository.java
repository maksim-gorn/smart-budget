package com.tpu.itr.smart_budget.budget.repository;

import com.tpu.itr.smart_budget.authentication.dto.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    List<TransactionEntity> findByUserAndDateBetween(UserEntity user, LocalDate startDate, LocalDate endDate);

    boolean existsByUserAndBankTransactionId(UserEntity user, Long bankTransactionId);
}

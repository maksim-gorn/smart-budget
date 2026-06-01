package com.tpu.itr.smart_budget.limits;

import com.tpu.itr.smart_budget.authentication.dto.UserEntity;
import com.tpu.itr.smart_budget.authentication.user.UserRepository;
import com.tpu.itr.smart_budget.budget.TransactionEntity;
import com.tpu.itr.smart_budget.budget.TransactionRepository;
import com.tpu.itr.smart_budget.common.BadRequestException;
import com.tpu.itr.smart_budget.common.MessageResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class LimitService {

    private final LimitRepository limitRepository;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public LimitService(LimitRepository limitRepository,
                        TransactionRepository transactionRepository,
                        UserRepository userRepository) {
        this.limitRepository = limitRepository;
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

    @Transactional
    public MessageResponse setLimit(Double limitAmount) {
        if (limitAmount == null || limitAmount <= 0) {
            throw new BadRequestException("Limit must be greater than 0");
        }

        UserEntity currentUser = getCurrentUser();

        LimitEntity limit = limitRepository.findByUser(currentUser)
                .orElse(new LimitEntity(limitAmount, currentUser));

        if (limit.getId() != null) {
            limit.setLimitAmount(limitAmount);
        }

        limitRepository.save(limit);
        return new MessageResponse("Success");
    }

    @Transactional(readOnly = true)
    public NotificationResponse getNotification() {
        UserEntity currentUser = getCurrentUser();

        LimitEntity limit = limitRepository.findByUser(currentUser)
                .orElse(null);

        if (limit == null) {
            return new NotificationResponse(0);
        }

        LocalDate now = LocalDate.now();
        LocalDate startOfMonth = now.withDayOfMonth(1);
        LocalDate endOfMonth = now.withDayOfMonth(now.lengthOfMonth());

        List<TransactionEntity> transactions = transactionRepository
                .findByUserAndDateBetween(currentUser, startOfMonth, endOfMonth);

        double totalExpenses = transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .mapToDouble(TransactionEntity::getAmount)
                .sum() * -1;

        double percent = (totalExpenses / limit.getLimitAmount()) * 100;

        return new NotificationResponse(Math.round(percent * 100) / 100.0);
    }
}
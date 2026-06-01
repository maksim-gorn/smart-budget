package com.tpu.itr.smart_budget.limits;

import com.tpu.itr.smart_budget.authentication.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface LimitRepository extends JpaRepository<LimitEntity, Long> {
    Optional<LimitEntity> findByUser(UserEntity user);
}
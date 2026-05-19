package com.tpu.itr.smart_budget.authentication.user;

import com.tpu.itr.smart_budget.authentication.dto.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByPhoneNumber(String phoneNumber);

    Optional<UserEntity> findByEmailOrPhoneNumber(
            String email,
            String phoneNumber
    );

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);
}

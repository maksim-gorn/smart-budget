package com.tpu.itr.smart_budget.transaction.usersApi;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserApiKeyRepository extends JpaRepository<UserApiKeyEntity, Long> {
}
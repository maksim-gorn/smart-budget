package com.tpu.itr.smart_budget.transaction.usersApi;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_api_keys")
public class UserApiKeyEntity {

    @Id
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "api_key", nullable = false)
    private String apiKey;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    protected UserApiKeyEntity() {
    }

    public UserApiKeyEntity(Long userId, String apiKey) {
        this.userId = userId;
        this.apiKey = apiKey;
        this.createdAt = LocalDateTime.now();
    }

    public Long getUserId() {
        return userId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
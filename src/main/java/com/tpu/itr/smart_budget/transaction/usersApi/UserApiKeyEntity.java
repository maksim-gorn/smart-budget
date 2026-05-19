package com.tpu.itr.smart_budget.transaction.usersApi;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.Month;

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



    @Column(name = "last_update", nullable = false)
    private LocalDateTime last_update;

    protected UserApiKeyEntity() {
    }

    public void setLast_update(LocalDateTime last_update) {
        this.last_update = last_update;
    }

    public LocalDateTime getLast_update() {
        return last_update;
    }

    public UserApiKeyEntity(Long userId, String apiKey) {
        this.userId = userId;
        this.apiKey = apiKey;
        this.createdAt = LocalDateTime.now();
        this.last_update=LocalDateTime.of(2000, 1, 1, 1, 1);
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
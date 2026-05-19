package com.tpu.itr.smart_budget.budget;

import java.time.LocalDate;

public class TransactionDTO {
    private Long id;
    private Long userId;
    private String merchant;
    private String category;
    private Long amount;
    private LocalDate date;

    public TransactionDTO() {}

    public TransactionDTO(TransactionEntity entity) {
        this.id = entity.getId();
        this.userId = entity.getUser().getId();
        this.merchant = entity.getMerchant();
        this.category = entity.getCategory();
        this.amount = entity.getAmount();
        this.date = entity.getDate();
    }

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getMerchant() { return merchant; }
    public void setMerchant(String merchant) { this.merchant = merchant; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Long getAmount() { return amount; }
    public void setAmount(Long amount) { this.amount = amount; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
}
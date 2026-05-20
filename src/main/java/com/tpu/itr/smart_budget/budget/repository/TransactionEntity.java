package com.tpu.itr.smart_budget.budget.repository;

import com.tpu.itr.smart_budget.authentication.dto.UserEntity;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "transactions")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bank_transaction_id", nullable = false)
    private Long bankTransactionId;

    @Column(nullable = false)
    private String merchant;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private Float amount;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String currency = "RUB";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    public TransactionEntity() {}

    public TransactionEntity(Long bankTransactionId, String merchant, String category, Float amount, LocalDate date,
                             String currency, UserEntity user) {
        this.bankTransactionId = bankTransactionId;
        this.merchant = merchant;
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.user = user;
        this.currency = currency;
    }

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getBankTransactionId() { return bankTransactionId; }
    public void setBankTransactionId(Long bankTransactionId) { this.bankTransactionId = bankTransactionId; }

    public String getMerchant() { return merchant; }
    public void setMerchant(String merchant) { this.merchant = merchant; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Float getAmount() { return amount; }
    public void setAmount(Float amount) { this.amount = amount; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public UserEntity getUser() { return user; }
    public void setUser(UserEntity user) { this.user = user; }
}

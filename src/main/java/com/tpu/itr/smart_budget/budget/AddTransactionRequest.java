package com.tpu.itr.smart_budget.budget;

import java.time.LocalDate;

public class AddTransactionRequest {
    private String merchant;
    private String category;
    private Long amount;
    private LocalDate date;

    public String getMerchant() { return merchant; }
    public void setMerchant(String merchant) { this.merchant = merchant; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Long getAmount() { return amount; }
    public void setAmount(Long amount) { this.amount = amount; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
}
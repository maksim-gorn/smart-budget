package com.tpu.itr.smart_budget.budget.dto;

import java.util.List;

public class GetSpendingsResponse {
    private int total;
    private List<TransactionDTO> items;

    public GetSpendingsResponse(List<TransactionDTO> items) {
        this.items = items;
        this.total = items.size();
    }

    public int getTotal() { return total; }
    public void setTotal(int total) { this.total = total; }

    public List<TransactionDTO> getItems() { return items; }
    public void setItems(List<TransactionDTO> items) { this.items = items; }
}
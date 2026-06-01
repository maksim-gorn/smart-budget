package com.tpu.itr.smart_budget.budget.api;

import java.util.List;

public interface BudgetTransactionImportPort {
    void importTransactions(Long userId, List<ImportedTransaction> transactions);
}

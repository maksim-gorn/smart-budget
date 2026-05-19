package com.tpu.itr.smart_budget.budget;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/budget")
public class BudgetController {

    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @PostMapping("/getspendings")
    public GetSpendingsResponse getSpendings(@RequestBody GetSpendingsRequest request) {  // ← убрали @Valid
        return budgetService.getSpendings(request.getStart_date(), request.getEnd_date());
    }

    @PatchMapping("/transaction/{id}")
    public TransactionDTO updateCategory(@PathVariable Long id, @RequestBody UpdateCategoryRequest request) {  // ← убрали @Valid
        return budgetService.updateCategory(id, request.getNew_category());
    }

    @PostMapping("/transaction/add")
    public TransactionDTO addTransaction(@RequestBody AddTransactionRequest request) {
        return budgetService.addTransaction(request);
    }
}
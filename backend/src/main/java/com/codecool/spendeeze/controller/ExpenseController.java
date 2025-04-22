package com.codecool.spendeeze.controller;

import com.codecool.spendeeze.model.dto.ExpenseWithAmountDateCategoryDTO;
import com.codecool.spendeeze.model.dto.ExpenseWithIdAmountDateCategoryDTO;
import com.codecool.spendeeze.model.entity.TransactionCategory;
import com.codecool.spendeeze.service.ExpenseService;
import com.codecool.spendeeze.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    private final ExpenseService expenseService;
    private final AuthUtil authUtil;

    @Autowired
    public ExpenseController(ExpenseService expenseService, AuthUtil authUtil) {
        this.expenseService = expenseService;
        this.authUtil = authUtil;
    }

    @GetMapping
    public List<ExpenseWithIdAmountDateCategoryDTO> getAllExpensesByMemberUsername() {
        String username = authUtil.getUsername();
        return expenseService.getAllExpensesByUsername(username);
    }

    @GetMapping("/{id}")
    public ExpenseWithIdAmountDateCategoryDTO getExpenseById(@PathVariable UUID id) {
        return expenseService.getExpenseDTOByPublicId(id);
    }

    @GetMapping("/category/{category}")
    public List<ExpenseWithIdAmountDateCategoryDTO> getExpensesOfMemberByCategory(@PathVariable TransactionCategory category) {
        String username = authUtil.getUsername();
        return expenseService.getExpensesByExpenseCategoryAndMemberUsername(category, username);
    }

    @PostMapping
    public ExpenseWithIdAmountDateCategoryDTO addExpense(@RequestBody ExpenseWithAmountDateCategoryDTO expenseDTO) {
        String username = authUtil.getUsername();
        return expenseService.addExpense(username, expenseDTO);
    }

    @PutMapping("/{id}")
    public ExpenseWithIdAmountDateCategoryDTO updateExpense(@PathVariable UUID id, @RequestBody ExpenseWithIdAmountDateCategoryDTO expenseDTO) {
        return expenseService.updateExpense(id, expenseDTO);
    }

    @DeleteMapping("/{id}")
    public int deleteExpense(@PathVariable UUID id) {
        return expenseService.deleteExpenseByPublicId(id);
    }

    @GetMapping("/current")
    public List<ExpenseWithIdAmountDateCategoryDTO> getCurrentMonthExpenses() {
        String username = authUtil.getUsername();
        return expenseService.getCurrentMonthExpenses(username);
    }
}

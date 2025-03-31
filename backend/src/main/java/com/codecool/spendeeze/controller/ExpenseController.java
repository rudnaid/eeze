package com.codecool.spendeeze.controller;

import com.codecool.spendeeze.model.dto.ExpenseWithAmountDateCategoryDTO;
import com.codecool.spendeeze.model.dto.ExpenseWithIdAmountDateCategoryDTO;
import com.codecool.spendeeze.model.entity.TransactionCategory;
import com.codecool.spendeeze.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    private final ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public List<ExpenseWithIdAmountDateCategoryDTO> getAllExpensesByMemberUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return expenseService.getAllExpensesByUsername(username);
    }

    @GetMapping("/{id}")
    public ExpenseWithIdAmountDateCategoryDTO getExpenseById(@PathVariable UUID id) {
        return expenseService.getExpenseDTOByPublicId(id);
    }

    @GetMapping("/category/{category}")
    public List<ExpenseWithIdAmountDateCategoryDTO> getExpensesOfMemberByCategory(@PathVariable TransactionCategory category) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return expenseService.getExpensesByExpenseCategoryAndMemberUsername(category, username);
    }

    @PostMapping
    public ExpenseWithIdAmountDateCategoryDTO addExpense(@RequestBody ExpenseWithAmountDateCategoryDTO expenseDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return expenseService.getCurrentMonthExpenses(username);
    }
}

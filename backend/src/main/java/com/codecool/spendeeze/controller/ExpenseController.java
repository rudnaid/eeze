package com.codecool.spendeeze.controller;

import com.codecool.spendeeze.dto.ExpenseRequestDTO;
import com.codecool.spendeeze.dto.ExpenseResponseDTO;
import com.codecool.spendeeze.service.ExpenseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping()
    public List<ExpenseResponseDTO> getAllExpensesByUserId(@RequestParam("userId") UUID userId) {
        return expenseService.getAllExpensesByUserId(userId);
    }

    @GetMapping("/{id}")
    public ExpenseResponseDTO getExpenseById(@PathVariable UUID id) {
        return expenseService.getExpenseById(id);
    }

    @GetMapping("/category{category}")
    public List<ExpenseResponseDTO> getExpensesByCategory(@PathVariable String category) {
        return expenseService.getExpensesByCategory(category);
    }

    @PostMapping()
    public UUID addExpense(@RequestBody ExpenseRequestDTO expenseDTO) {
        return expenseService.addExpense(expenseDTO);
    }

    @PutMapping("/{id}")
    public UUID updateExpense(@PathVariable UUID id, @RequestBody ExpenseRequestDTO expenseDTO) {
        return expenseService.updateExpense(id, expenseDTO);
    }

    @DeleteMapping("/{id}")
    public UUID deleteExpense(@PathVariable UUID id) {
        return expenseService.deleteExpense(id);
    }
}

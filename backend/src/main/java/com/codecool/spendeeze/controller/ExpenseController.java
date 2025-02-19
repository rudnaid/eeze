package com.codecool.spendeeze.controller;

import com.codecool.spendeeze.model.ExpenseCategory;
import com.codecool.spendeeze.model.dto.ExpenseRequestDTO;
import com.codecool.spendeeze.model.dto.ExpenseResponseDTO;
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
    public List<ExpenseResponseDTO> getAllExpensesByMemberPublicId(@RequestParam("memberPublicId") UUID memberPublicId) {
        return expenseService.getAllExpensesByMemberPublicId(memberPublicId);
    }

    @GetMapping("/{id}")
    public ExpenseResponseDTO getExpenseById(@PathVariable UUID id) {
        return expenseService.getExpenseDTOByPublicId(id);
    }

    @GetMapping("/category{category}")
    public List<ExpenseResponseDTO> getExpensesOfMemberByCategory(@PathVariable ExpenseCategory category, @RequestParam UUID memberPublicId) {
        return expenseService.getExpensesByExpenseCategoryAndMemberPublicId(category, memberPublicId);
    }

    @PostMapping()
    public ExpenseResponseDTO addExpense(@RequestParam UUID memberPublicId, @RequestBody ExpenseRequestDTO expenseDTO) {
        return expenseService.addExpense(memberPublicId, expenseDTO);
    }

    @PutMapping("/{id}")
    public ExpenseResponseDTO updateExpense(@PathVariable UUID id, @RequestBody ExpenseResponseDTO expenseDTO) {
        return expenseService.updateExpense(id, expenseDTO);
    }

    @DeleteMapping("/{id}")
    public int deleteExpense(@PathVariable UUID id) {
        return expenseService.deleteExpenseByPublicId(id);
    }
}

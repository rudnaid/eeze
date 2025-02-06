package com.codecool.spendeeze.repository;

import com.codecool.spendeeze.model.dto.ExpenseRequestDTO;
import com.codecool.spendeeze.model.Expense;

import java.util.List;
import java.util.UUID;

public interface ExpenseDAO {
    List<Expense> getAllExpensesByUserId(UUID userId);

    Expense getExpenseById(UUID id);

    List<Expense> getExpensesByCategory(String category);

    UUID addExpense(ExpenseRequestDTO expenseDTO);

    UUID updateExpense(UUID id, ExpenseRequestDTO expenseDTO);

    UUID deleteExpense(UUID id);
}

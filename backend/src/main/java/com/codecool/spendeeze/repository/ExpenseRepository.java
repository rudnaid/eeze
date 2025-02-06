package com.codecool.spendeeze.repository;

import com.codecool.spendeeze.model.dto.ExpenseRequestDTO;
import com.codecool.spendeeze.model.Expense;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class ExpenseRepository implements ExpenseDAO {
    private final Map<UUID, Expense> expenses = new HashMap<>();

    @Override
    public List<Expense> getAllExpensesByUserId(UUID userId) {
        return expenses.values().stream()
                .filter(expense -> expense.getUserId().equals(userId))
                .toList();
    }

    @Override
    public Expense getExpenseById(UUID id) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public List<Expense> getExpensesByCategory(String category) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public UUID addExpense(ExpenseRequestDTO expense) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public UUID updateExpense(UUID id, ExpenseRequestDTO expense) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public UUID deleteExpense(UUID id) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

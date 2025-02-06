package com.codecool.spendeeze.service;

import com.codecool.spendeeze.model.dto.ExpenseRequestDTO;
import com.codecool.spendeeze.model.dto.ExpenseResponseDTO;
import com.codecool.spendeeze.model.Expense;
import com.codecool.spendeeze.repository.ExpenseDAO;
import com.codecool.spendeeze.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseDAO expenseDAO, ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public List<ExpenseResponseDTO> getAllExpensesByUserId(UUID userId) {
        List<Expense> expenses = expenseRepository.getAllExpensesByUserId(userId);

        return expenses.stream()
                .map(expense -> new ExpenseResponseDTO(
                        expense.getAmount(),
                        expense.getTransactionDate(),
                        expense.getExpenseCategory().name()
                ))
                .toList();
    }

    public ExpenseResponseDTO getExpenseById(UUID id) {
        Expense expense = expenseRepository.getExpenseById(id);

        if (expense == null) {
            throw new NoSuchElementException("Expense with id " + id + " not found");
        }

        return new ExpenseResponseDTO(
                expense.getAmount(),
                expense.getTransactionDate(),
                expense.getExpenseCategory().name());
    }

    public List<ExpenseResponseDTO> getExpensesByCategory(String category) {
        List<Expense> expenses = expenseRepository.getExpensesByCategory(category);

        return expenses.stream()
                .map(expense ->  new ExpenseResponseDTO(
                        expense.getAmount(),
                        expense.getTransactionDate(),
                        expense.getExpenseCategory().name()))
                .toList();
    }

    public UUID addExpense(ExpenseRequestDTO expenseDTO) {
        return expenseRepository.addExpense(expenseDTO);
    }

    public UUID updateExpense(UUID id, ExpenseRequestDTO expenseDTO) {
        return expenseRepository.updateExpense(id, expenseDTO);
    }

    public UUID deleteExpense(UUID id) {
        return expenseRepository.deleteExpense(id);
    }

}

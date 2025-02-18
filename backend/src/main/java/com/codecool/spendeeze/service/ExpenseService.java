package com.codecool.spendeeze.service;

import com.codecool.spendeeze.model.ExpenseCategory;
import com.codecool.spendeeze.model.dto.ExpenseRequestDTO;
import com.codecool.spendeeze.model.dto.ExpenseResponseDTO;
import com.codecool.spendeeze.model.entity.Expense;
import com.codecool.spendeeze.model.entity.User;
import com.codecool.spendeeze.repository.ExpenseRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class ExpenseService {
    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public ExpenseResponseDTO getExpenseDTOByPublicId(UUID id) {
        Expense expense = getExpenseByPublicId(id);
        return convertToExpenseResponseDTO(expense);
    }

    private Expense getExpenseByPublicId(UUID id) {
        Optional<Expense> expense = expenseRepository.findExpenseByPublicId(id);
        return expense.orElseThrow(NoSuchElementException::new);
    }

    public ExpenseResponseDTO addExpense(UUID userPublicId, ExpenseRequestDTO expenseDTO) {
        User user = userRepository.findUserByPublicId(userPublicId);
        Expense expense = convertToExpense(expenseDTO);

        expense.setUser(user);
        expenseRepository.save(expense);

        return convertToExpenseResponseDTO(expense);
    }

    public ExpenseResponseDTO updateExpense(UUID publicId, ExpenseResponseDTO expenseDTO) {
        Expense expenseToUpdate = getExpenseByPublicId(publicId);

        expenseToUpdate.setAmount(expenseDTO.amount());
        expenseToUpdate.setTransactionDate(expenseDTO.transactionDate());
        expenseToUpdate.setExpenseCategory(ExpenseCategory.valueOf(expenseDTO.expenseCategory()));

        expenseRepository.save(expenseToUpdate);

        return convertToExpenseResponseDTO(expenseToUpdate);
    }

    public UUID deleteExpenseByPublicId(UUID publicId) {
        return expenseRepository.deleteExpenseByPublicId(publicId);
    }


    private ExpenseResponseDTO convertToExpenseResponseDTO(Expense expense) {
        return new ExpenseResponseDTO(
                expense.getPublicId(),
                expense.getAmount(),
                expense.getTransactionDate(),
                expense.getExpenseCategory().name());
    }

    private Expense convertToExpense(ExpenseRequestDTO expenseRequestDTO) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.convertValue(expenseRequestDTO, Expense.class);
        } catch (Exception e) {
            throw new RuntimeException("Could not convert expense requestDTO to Expense", e.getCause());
        }
    }

    public List<ExpenseResponseDTO> getAllExpensesByUserPublicId(UUID userPublicId) {
        return expenseRepository.getExpensesByUserPublicId(userPublicId);
    }

    public List<ExpenseResponseDTO> getExpensesByExpenseCategoryAndUserPublicId(String category, UUID userPublicId) {
        return expenseRepository.getExpensesByExpenseCategoryAndUserPublicId(category, userPublicId);


    }
}

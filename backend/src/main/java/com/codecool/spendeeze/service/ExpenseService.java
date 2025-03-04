package com.codecool.spendeeze.service;

import com.codecool.spendeeze.model.dto.ExpenseRequestDTO;
import com.codecool.spendeeze.model.dto.ExpenseResponseDTO;
import com.codecool.spendeeze.model.entity.Expense;
import com.codecool.spendeeze.model.entity.Member;
import com.codecool.spendeeze.model.entity.TransactionCategory;
import com.codecool.spendeeze.repository.ExpenseRepository;
import com.codecool.spendeeze.repository.MemberRepository;
import com.codecool.spendeeze.repository.TransactionCategoryRepository;
import com.codecool.spendeeze.security.jwt.JwtUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final MemberRepository memberRepository;
    private final TransactionCategoryRepository transactionCategoryRepository;

    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository, MemberRepository memberRepository, TransactionCategoryRepository transactionCategoryRepository, JwtUtils jwtUtils) {
        this.expenseRepository = expenseRepository;
        this.memberRepository = memberRepository;
        this.transactionCategoryRepository = transactionCategoryRepository;
    }

    public ExpenseResponseDTO getExpenseDTOByPublicId(UUID id) {
        Expense expense = getExpenseByPublicId(id);
        return convertToExpenseResponseDTO(expense);
    }

    private Expense getExpenseByPublicId(UUID id) {
        Optional<Expense> expense = expenseRepository.findExpenseByPublicId(id);
        return expense.orElseThrow(NoSuchElementException::new);
    }

    public ExpenseResponseDTO addExpense(String username, ExpenseRequestDTO expenseDTO) {
        Member member = memberRepository.findMemberByUsername(username).orElseThrow(NoSuchElementException::new);
        Expense expense = convertToExpense(expenseDTO);

        expense.setMember(member);
        expenseRepository.save(expense);

        return convertToExpenseResponseDTO(expense);
    }

    public ExpenseResponseDTO updateExpense(UUID publicId, ExpenseResponseDTO expenseDTO) {
        Expense expenseToUpdate = getExpenseByPublicId(publicId);
        TransactionCategory category = transactionCategoryRepository.getTransactionCategoryByName(expenseDTO.expenseCategory());

        expenseToUpdate.setAmount(expenseDTO.amount());
        expenseToUpdate.setTransactionDate(expenseDTO.transactionDate());
        expenseToUpdate.setTransactionCategory(category);

        expenseRepository.save(expenseToUpdate);

        return convertToExpenseResponseDTO(expenseToUpdate);
    }

    public int deleteExpenseByPublicId(UUID publicId) {
        return expenseRepository.deleteExpenseByPublicId(publicId);
    }


    private ExpenseResponseDTO convertToExpenseResponseDTO(Expense expense) {
        return new ExpenseResponseDTO(
                expense.getPublicId(),
                expense.getAmount(),
                expense.getTransactionDate(),
                expense.getTransactionCategory().getName());
    }

    private Expense convertToExpense(ExpenseRequestDTO expenseRequestDTO) {
        try {
            Expense expense = new Expense();
            expense.setAmount(expenseRequestDTO.amount());
            expense.setTransactionDate(expenseRequestDTO.transactionDate());

            TransactionCategory category = transactionCategoryRepository.getTransactionCategoryByName(expenseRequestDTO.category());
            expense.setTransactionCategory(category);
            return expense;
        } catch (Exception e) {
            throw new RuntimeException("Could not convert expense requestDTO to Expense", e.getCause());
        }
    }

    public List<ExpenseResponseDTO> getAllExpensesByUsername(String username) {
        List<Expense> expenses = expenseRepository.getExpensesByMemberUsername(username);

        return expenses.stream()
                .map(this::convertToExpenseResponseDTO)
                .toList();
    }

    public List<ExpenseResponseDTO> getExpensesByExpenseCategoryAndMemberUsername(TransactionCategory category, String username) {
        List<Expense> expenses = expenseRepository.getExpensesByTransactionCategoryAndMemberUsername(category, username);

        return expenses.stream()
                .map(this::convertToExpenseResponseDTO)
                .toList();
    }
}

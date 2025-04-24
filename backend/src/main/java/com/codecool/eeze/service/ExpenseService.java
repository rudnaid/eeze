package com.codecool.spendeeze.service;

import com.codecool.spendeeze.model.dto.ExpenseWithAmountDateCategoryDTO;
import com.codecool.spendeeze.model.dto.ExpenseWithIdAmountDateCategoryDTO;
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

import java.time.LocalDate;
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

    public ExpenseWithIdAmountDateCategoryDTO getExpenseDTOByPublicId(UUID id) {
        Expense expense = getExpenseByPublicId(id);
        return convertToExpenseResponseDTO(expense);
    }

    private Expense getExpenseByPublicId(UUID id) {
        Optional<Expense> expense = expenseRepository.findExpenseByPublicId(id);
        return expense.orElseThrow(NoSuchElementException::new);
    }

    public ExpenseWithIdAmountDateCategoryDTO addExpense(String username, ExpenseWithAmountDateCategoryDTO expenseDTO) {
        Member member = memberRepository.findMemberByUsername(username).orElseThrow(NoSuchElementException::new);
        Expense expense = convertToExpense(expenseDTO);

        expense.setMember(member);
        expenseRepository.save(expense);

        return convertToExpenseResponseDTO(expense);
    }

    public ExpenseWithIdAmountDateCategoryDTO updateExpense(UUID publicId, ExpenseWithIdAmountDateCategoryDTO expenseDTO) {
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


    private ExpenseWithIdAmountDateCategoryDTO convertToExpenseResponseDTO(Expense expense) {
        return new ExpenseWithIdAmountDateCategoryDTO(
                expense.getPublicId(),
                expense.getAmount(),
                expense.getTransactionDate(),
                expense.getTransactionCategory().getName());
    }

    private Expense convertToExpense(ExpenseWithAmountDateCategoryDTO expenseWithAmountDateCategoryDTO) {
        try {
            Expense expense = new Expense();
            expense.setAmount(expenseWithAmountDateCategoryDTO.amount());
            expense.setTransactionDate(expenseWithAmountDateCategoryDTO.transactionDate());

            TransactionCategory category = transactionCategoryRepository.getTransactionCategoryByName(expenseWithAmountDateCategoryDTO.category());
            expense.setTransactionCategory(category);
            return expense;
        } catch (Exception e) {
            throw new RuntimeException("Could not convert expense requestDTO to Expense", e.getCause());
        }
    }

    public List<ExpenseWithIdAmountDateCategoryDTO> getAllExpensesByUsername(String username) {
        List<Expense> expenses = expenseRepository.getExpensesByMemberUsername(username);

        return expenses.stream()
                .map(this::convertToExpenseResponseDTO)
                .toList();
    }

    private Member getMemberByUsername(String username) {
        return memberRepository.findMemberByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("Member with username: " + username + " not found"));
    }

    private Expense getExpenseByPublicId(UUID publicId) {
        return expenseRepository.findExpenseByPublicId(publicId)
                .orElseThrow(() -> new NoSuchElementException("Expense with publicId" + publicId + "not found."));
    }
}

package com.codecool.eeze.service;

import com.codecool.eeze.model.dto.ExpenseWithAmountDateCategoryDTO;
import com.codecool.eeze.model.dto.ExpenseWithIdAmountDateCategoryDTO;
import com.codecool.eeze.model.entity.Expense;
import com.codecool.eeze.model.entity.Member;
import com.codecool.eeze.model.entity.TransactionCategory;
import com.codecool.eeze.repository.ExpenseRepository;
import com.codecool.eeze.repository.MemberRepository;
import com.codecool.eeze.repository.TransactionCategoryRepository;
import com.codecool.eeze.security.jwt.JwtUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
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

    public ExpenseWithIdAmountDateCategoryDTO getExpenseDTOByPublicId(UUID publicId) {
        Expense expense = getExpenseByPublicId(publicId);

        return convertToExpenseResponseDTO(expense);
    }

    public List<ExpenseWithIdAmountDateCategoryDTO> getAllExpensesByUsername(String username) {
        List<Expense> expenses = expenseRepository.getExpensesByMemberUsername(username);

        return expenses.stream()
                .map(this::convertToExpenseResponseDTO)
                .toList();
    }

    public List<ExpenseWithIdAmountDateCategoryDTO> getExpensesByExpenseCategoryAndMemberUsername(TransactionCategory category, String username) {
        List<Expense> expenses = expenseRepository.getExpensesByTransactionCategoryAndMemberUsername(category, username);

        return expenses.stream()
                .map(this::convertToExpenseResponseDTO)
                .toList();
    }

    public List<ExpenseWithIdAmountDateCategoryDTO> getCurrentMonthExpenses(String username) {
        LocalDate currentDate = LocalDate.now();
        int currentMonth = currentDate.getMonthValue();
        int currentYear = currentDate.getYear();

        return getCurrentMonthExpenses(username, currentMonth, currentYear);

    }

    public List<ExpenseWithIdAmountDateCategoryDTO> getCurrentMonthExpenses(String username, int month, int year) {
        List<Expense> expenses = expenseRepository.getMonthlyExpensesByUsernameAndMonthAndYear(username, month, year);

        return expenses.stream()
                .map(this::convertToExpenseResponseDTO)
                .toList();
    }

    public ExpenseWithIdAmountDateCategoryDTO addExpense(String username, ExpenseWithAmountDateCategoryDTO expenseDTO) {
        Member member = getMemberByUsername(username);

        Expense expense = convertToExpense(expenseDTO);

        expense.setMember(member);
        expenseRepository.save(expense);

        return convertToExpenseResponseDTO(expense);
    }

    @Transactional
    public ExpenseWithIdAmountDateCategoryDTO updateExpense(UUID publicId, ExpenseWithIdAmountDateCategoryDTO expenseDTO) {
        Expense expenseToUpdate = getExpenseByPublicId(publicId);

        TransactionCategory category = transactionCategoryRepository.getTransactionCategoryByName(expenseDTO.expenseCategory());

        expenseToUpdate.setAmount(expenseDTO.amount());
        expenseToUpdate.setTransactionDate(expenseDTO.transactionDate());
        expenseToUpdate.setTransactionCategory(category);

        expenseRepository.save(expenseToUpdate);

        return convertToExpenseResponseDTO(expenseToUpdate);
    }

    @Transactional
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
            throw new IllegalStateException("Failed to convert ExpenseRequestDTO", e);
        }
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

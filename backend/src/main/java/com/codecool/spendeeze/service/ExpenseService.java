package com.codecool.spendeeze.service;

import com.codecool.spendeeze.model.ExpenseCategory;
import com.codecool.spendeeze.model.dto.ExpenseRequestDTO;
import com.codecool.spendeeze.model.dto.ExpenseResponseDTO;
import com.codecool.spendeeze.model.entity.Expense;
import com.codecool.spendeeze.model.entity.Member;
import com.codecool.spendeeze.repository.ExpenseRepository;
import com.codecool.spendeeze.repository.MemberRepository;
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

    private final MemberRepository memberRepository;

    public ExpenseService(ExpenseRepository expenseRepository, MemberRepository memberRepository) {
        this.expenseRepository = expenseRepository;
        this.memberRepository = memberRepository;
    }

    public ExpenseResponseDTO getExpenseDTOByPublicId(UUID id) {
        Expense expense = getExpenseByPublicId(id);
        return convertToExpenseResponseDTO(expense);
    }

    private Expense getExpenseByPublicId(UUID id) {
        Optional<Expense> expense = expenseRepository.findExpenseByPublicId(id);
        return expense.orElseThrow(NoSuchElementException::new);
    }

    public ExpenseResponseDTO addExpense(UUID memberPublicId, ExpenseRequestDTO expenseDTO) {
        Member member = memberRepository.getMemberByPublicId(memberPublicId).orElseThrow(NoSuchElementException::new);
        Expense expense = convertToExpense(expenseDTO);

        expense.setMember(member);
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

    public int deleteExpenseByPublicId(UUID publicId) {
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

    public List<ExpenseResponseDTO> getAllExpensesByMemberPublicId(UUID memberPublicId) {
        List<Expense> expenses = expenseRepository.getExpensesByMemberPublicId(memberPublicId);

        return expenses.stream()
                .map(this::convertToExpenseResponseDTO)
                .toList();
    }

    public List<ExpenseResponseDTO> getExpensesByExpenseCategoryAndMemberPublicId(ExpenseCategory category, UUID memberPublicId) {
        List<Expense> expenses = expenseRepository.getExpensesByExpenseCategoryAndMemberPublicId(category, memberPublicId);

        return expenses.stream()
                .map(this::convertToExpenseResponseDTO)
                .toList();
    }
}

package com.codecool.spendeeze.service;

import com.codecool.spendeeze.model.dto.ExpenseRequestDTO;
import com.codecool.spendeeze.model.dto.ExpenseResponseDTO;
import com.codecool.spendeeze.model.entity.Expense;
import com.codecool.spendeeze.model.entity.Member;
import com.codecool.spendeeze.model.entity.TransactionCategory;
import com.codecool.spendeeze.repository.ExpenseRepository;
import com.codecool.spendeeze.repository.MemberRepository;
import com.codecool.spendeeze.repository.TransactionCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExpenseServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private TransactionCategoryRepository transactionCategoryRepository;

    @InjectMocks
    private ExpenseService expenseService;

    private Member member;
    private Expense expense;
    private ExpenseRequestDTO expenseRequestDTO;
    private ExpenseResponseDTO expenseResponseDTO;
    private TransactionCategory category;
    private UUID expenseId;

    @BeforeEach
    void setUp() {
        expenseId = UUID.randomUUID();

        member = new Member();
        member.setId(1L);
        member.setUsername("testUser");

        category = new TransactionCategory();
        category.setId(1L);
        category.setName("Food");

        expense = new Expense();
        expense.setPublicId(expenseId);
        expense.setAmount(500.00);
        expense.setTransactionDate(LocalDate.of(2025, 3, 5));
        expense.setMember(member);
        expense.setTransactionCategory(category);

        expenseRequestDTO = new ExpenseRequestDTO(500.00, LocalDate.of(2025, 3, 5), "Food");

        expenseResponseDTO = new ExpenseResponseDTO(expenseId, 500.00, LocalDate.of(2025, 3, 5), "Food");
    }

    @DisplayName("JUnit test for ExpenseService - addExpense()")
    @Test
    void givenExpenseRequestDTOAndUsername_whenAddExpense_thenReturnSavedExpenseResponseDTO() {
        // GIVEN
        given(memberRepository.findMemberByUsername("testUser")).willReturn(Optional.of(member));
        given(transactionCategoryRepository.getTransactionCategoryByName("Food")).willReturn(category);
        given(expenseRepository.save(any(Expense.class))).willReturn(expense);

        // WHEN
        ExpenseResponseDTO savedExpenseResponseDTO = expenseService.addExpense("testUser", expenseRequestDTO);

        // THEN
        assertThat(savedExpenseResponseDTO).isNotNull();
        assertThat(savedExpenseResponseDTO.amount()).isEqualTo(expenseRequestDTO.amount());
        assertThat(savedExpenseResponseDTO.transactionDate()).isEqualTo(expenseRequestDTO.transactionDate());
        assertThat(savedExpenseResponseDTO.expenseCategory()).isEqualTo(expenseRequestDTO.category());

        // Verify output
        verify(memberRepository, times(1)).findMemberByUsername("testUser");
        verify(transactionCategoryRepository, times(1)).getTransactionCategoryByName("Food");
        verify(expenseRepository, times(1)).save(any(Expense.class));
    }

    @DisplayName("JUnit test for ExpenseService - addExpense() should throw exception if member not found")
    @Test
    void givenInvalidUsername_whenAddExpense_thenThrowException() {
        // GIVEN
        given(memberRepository.findMemberByUsername("invalidUser")).willReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(NoSuchElementException.class, () -> {
            expenseService.addExpense("invalidUser", expenseRequestDTO);
        });

        // Verify repository calls
        verify(memberRepository, times(1)).findMemberByUsername("invalidUser");
        verify(expenseRepository, never()).save(any(Expense.class));
    }
}
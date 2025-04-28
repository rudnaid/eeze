package com.codecool.eeze.service;

import com.codecool.eeze.model.dto.ExpenseWithAmountDateCategoryDTO;
import com.codecool.eeze.model.dto.ExpenseWithIdAmountDateCategoryDTO;
import com.codecool.eeze.model.entity.Expense;
import com.codecool.eeze.model.entity.Member;
import com.codecool.eeze.model.entity.TransactionCategory;
import com.codecool.eeze.repository.ExpenseRepository;
import com.codecool.eeze.repository.MemberRepository;
import com.codecool.eeze.repository.TransactionCategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private TransactionCategoryRepository transactionCategoryRepository;

    @Mock
    private Member member;

    @Mock
    private Expense expense;

    @Mock
    private TransactionCategory category;

    @InjectMocks
    private ExpenseService expenseService;

    @DisplayName("JUnit test for ExpenseService - getExpenseDTOByPublicId()")
    @Test
    void givenValidExpenseId_whenGetExpenseDTOByPublicId_thenReturnExpenseWithIdAmountDateCategoryDTO() {
        // GIVEN
        UUID expenseId = UUID.randomUUID();

        given(expenseRepository.findExpenseByPublicId(expenseId)).willReturn(Optional.of(expense));
        given(expense.getAmount()).willReturn(500.00);
        given(expense.getTransactionDate()).willReturn(LocalDate.of(2025, 3, 5));
        given(expense.getTransactionCategory()).willReturn(category);
        given(category.getName()).willReturn("Food");

        // WHEN
        ExpenseWithIdAmountDateCategoryDTO foundExpense = expenseService.getExpenseDTOByPublicId(expenseId);

        // THEN
        assertThat(foundExpense).isNotNull();
        assertThat(foundExpense.amount()).isEqualTo(500.00);
        assertThat(foundExpense.transactionDate()).isEqualTo(LocalDate.of(2025, 3, 5));
        assertThat(foundExpense.expenseCategory()).isEqualTo("Food");

        verify(expenseRepository, times(1)).findExpenseByPublicId(expenseId);
    }

    @DisplayName("JUnit test for ExpenseService - getExpenseDTOByPublicId() should throw exception if expense not found")
    @Test
    void givenInvalidExpenseId_whenGetExpenseDTOByPublicId_thenThrowException() {
        // GIVEN
        UUID invalidExpenseId = UUID.randomUUID();
        given(expenseRepository.findExpenseByPublicId(invalidExpenseId)).willReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(NoSuchElementException.class, () -> {
            expenseService.getExpenseDTOByPublicId(invalidExpenseId);
        });

        verify(expenseRepository, times(1)).findExpenseByPublicId(invalidExpenseId);
    }

    @DisplayName("JUnit test for ExpenseService - addExpense() saves new expense")
    @Test
    void givenExpenseWithAmountDateCategoryDTOAndUsername_whenAddExpense_thenReturnSavedExpenseWithIdAmountDateCategoryDTO() {
        // GIVEN
        ExpenseWithAmountDateCategoryDTO dto = new ExpenseWithAmountDateCategoryDTO(500.00, LocalDate.of(2025, 3, 5), "Food");
        UUID expenseId = UUID.randomUUID();

        Member member = new Member();
        member.setUsername("testUser");

        TransactionCategory category = new TransactionCategory();
        category.setName("Food");

        Expense expense = new Expense();
        expense.setPublicId(expenseId);
        expense.setTransactionDate(LocalDate.of(2025, 3, 5));
        expense.setAmount(500.00);
        expense.setTransactionCategory(category);

        given(memberRepository.findMemberByUsername("testUser")).willReturn(Optional.of(member));
        given(transactionCategoryRepository.getTransactionCategoryByName("Food")).willReturn(category);
        given(expenseRepository.save(any(Expense.class))).willReturn(expense);

        // WHEN
        ExpenseWithIdAmountDateCategoryDTO result = expenseService.addExpense("testUser", dto);

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.amount()).isEqualTo(500.00);
        assertThat(result.transactionDate()).isEqualTo(LocalDate.of(2025, 3, 5));
        assertThat(result.expenseCategory()).isEqualTo("Food");

        verify(memberRepository, times(1)).findMemberByUsername("testUser");
        verify(transactionCategoryRepository, times(1)).getTransactionCategoryByName("Food");
        verify(expenseRepository, times(1)).save(any(Expense.class));
    }

    @DisplayName("JUnit test for ExpenseService - addExpense() should throw exception if member not found")
    @Test
    void givenInvalidUsername_whenAddExpense_thenThrowException() {
        // GIVEN
        ExpenseWithAmountDateCategoryDTO dto = new ExpenseWithAmountDateCategoryDTO(500.00, LocalDate.of(2025, 3, 5), "Food");

        given(memberRepository.findMemberByUsername("invalidUser")).willReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(NoSuchElementException.class, () -> {
            expenseService.addExpense("invalidUser", dto);
        });

        verify(memberRepository, times(1)).findMemberByUsername("invalidUser");
        verify(expenseRepository, never()).save(any(Expense.class));
    }

    @DisplayName("JUnit test for ExpenseService - updateExpense()")
    @Test
    void givenValidExpenseID_whenUpdateExpense_thenReturnUpdatedExpenseWithIdAmountDateCategoryDTO() {
        // GIVEN
        UUID expenseId = UUID.randomUUID();

        given(expenseRepository.findExpenseByPublicId(expenseId)).willReturn(Optional.empty());

        // THEN & WHEN
        assertThrows(NoSuchElementException.class, () -> expenseService.updateExpense(expenseId, new ExpenseWithIdAmountDateCategoryDTO(expenseId, 500.00, LocalDate.now(), "Food")));

        verify(expenseRepository, never()).save(any(Expense.class));
    }

    @DisplayName("JUnit test for ExpenseService - updateExpense() should throw exception if expense not found")
    @Test
    void givenInvalidExpenseId_whenUpdateExpense_thenThrowException() {
        // GIVEN
        UUID expenseId = UUID.randomUUID();

        given(expenseRepository.findExpenseByPublicId(expenseId)).willReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(NoSuchElementException.class, () -> expenseService.updateExpense(expenseId, new ExpenseWithIdAmountDateCategoryDTO(expenseId, 500.00, LocalDate.now(), "Food")));

        verify(expenseRepository, never()).save(any(Expense.class));
    }

    @DisplayName("JUnit test for ExpenseService - deleteExpenseByPublicId()")
    @Test
    void givenValidExpenseId_whenDeleteExpenseByPublicId_thenDeleteExpense() {
        // GIVEN
        UUID expenseId = UUID.randomUUID();

        given(expenseRepository.deleteExpenseByPublicId(expenseId)).willReturn(1);

        // WHEN
        int deletedRows = expenseService.deleteExpenseByPublicId(expenseId);

        // THEN
        assertThat(deletedRows).isEqualTo(1);
        verify(expenseRepository, times(1)).deleteExpenseByPublicId(expenseId);
    }

    @DisplayName("JUnit test for ExpenseService - getAllExpensesByUserName()")
    @Test
    void givenUserName_whenGetAllExpensesByUserName_thenReturnAllExpenses() {
        // GIVEN
        given(expenseRepository.getExpensesByMemberUsername("testUser")).willReturn(List.of(expense));
        given(expense.getPublicId()).willReturn(UUID.randomUUID());
        given(expense.getAmount()).willReturn(500.00);
        given(expense.getTransactionDate()).willReturn(LocalDate.of(2025, 3, 5));
        given(expense.getTransactionCategory()).willReturn(category);
        given(category.getName()).willReturn("Food");

        // WHEN
        List<ExpenseWithIdAmountDateCategoryDTO> actualExpenses = expenseService.getAllExpensesByUsername("testUser");

        // THEN
        assertThat(actualExpenses).hasSize(1);
        assertThat(actualExpenses.get(0).expenseCategory()).isEqualTo("Food");

        verify(expenseRepository, times(1)).getExpensesByMemberUsername("testUser");
    }

    @DisplayName("JUnit test for ExpenseService - getExpensesByExpenseCategoryAndMemberUsername()")
    @Test
    void givenCategoryAndUsername_whenGetExpensesByExpenseCategoryAndMemberUsername_thenReturnExpenseList() {
        // GIVEN
        given(expenseRepository.getExpensesByTransactionCategoryAndMemberUsername(category, "testUser")).willReturn(List.of(expense));
        given(expense.getPublicId()).willReturn(UUID.randomUUID());
        given(expense.getAmount()).willReturn(500.00);
        given(expense.getTransactionDate()).willReturn(LocalDate.of(2025, 3, 5));
        given(expense.getTransactionCategory()).willReturn(category);
        given(category.getName()).willReturn("Food");

        // WHEN
        List<ExpenseWithIdAmountDateCategoryDTO> actualExpenses = expenseService.getExpensesByExpenseCategoryAndMemberUsername(category, "testUser");

        // THEN
        assertThat(actualExpenses).hasSize(1);
        assertThat(actualExpenses.get(0).expenseCategory()).isEqualTo("Food");

        verify(expenseRepository, times(1)).getExpensesByTransactionCategoryAndMemberUsername(category, "testUser");
    }
}

package com.codecool.spendeeze.service;

import com.codecool.spendeeze.model.dto.ExpenseWithAmountDateCategoryDTO;
import com.codecool.spendeeze.model.dto.ExpenseWithIdAmountDateCategoryDTO;
import com.codecool.spendeeze.model.entity.Expense;
import com.codecool.spendeeze.model.entity.Member;
import com.codecool.spendeeze.model.entity.TransactionCategory;
import com.codecool.spendeeze.repository.ExpenseRepository;
import com.codecool.spendeeze.repository.MemberRepository;
import com.codecool.spendeeze.repository.TransactionCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
    private ExpenseWithAmountDateCategoryDTO expenseWithAmountDateCategoryDTO;
    private ExpenseWithIdAmountDateCategoryDTO expenseWithIdAmountDateCategoryDTO;
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

        expenseWithAmountDateCategoryDTO = new ExpenseWithAmountDateCategoryDTO(500.00, LocalDate.of(2025, 3, 5), "Food");

        expenseWithIdAmountDateCategoryDTO = new ExpenseWithIdAmountDateCategoryDTO(expenseId, 500.00, LocalDate.of(2025, 3, 5), "Food");
    }

    @DisplayName("JUnit test for ExpenseService - getExpenseDTOByPublicId()")
    @Test
    void givenValidExpenseId_whenGetExpenseDTOByPublicId_thenReturnExpenseWithIdAmountDateCategoryDTO() {
        // GIVEN
        given(expenseRepository.findExpenseByPublicId(expenseId)).willReturn(Optional.of(expense));

        // WHEN
        ExpenseWithIdAmountDateCategoryDTO foundExpense = expenseService.getExpenseDTOByPublicId(expenseId);

        // THEN
        assertThat(foundExpense).isNotNull();
        assertThat(foundExpense.amount()).isEqualTo(expense.getAmount());
        assertThat(foundExpense.transactionDate()).isEqualTo(expense.getTransactionDate());
        assertThat(foundExpense.expenseCategory()).isEqualTo(expense.getTransactionCategory().getName());

        // Verify output
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

        // Verify output
        verify(expenseRepository, times(1)).findExpenseByPublicId(invalidExpenseId);
    }

    @DisplayName("JUnit test for ExpenseService - addExpense() saves new expense")
    @Test
    void givenExpenseRequestDTOAndUsername_whenAddExpense_thenReturnSavedExpenseResponseDTO() {
        // GIVEN
        given(memberRepository.findMemberByUsername("testUser")).willReturn(Optional.of(member));
        given(transactionCategoryRepository.getTransactionCategoryByName("Food")).willReturn(category);
        given(expenseRepository.save(any(Expense.class))).willReturn(expense);

        // WHEN
        ExpenseWithIdAmountDateCategoryDTO savedExpenseWithIdAmountDateCategoryDTO = expenseService.addExpense("testUser", expenseWithAmountDateCategoryDTO);

        // THEN
        assertThat(savedExpenseWithIdAmountDateCategoryDTO).isNotNull();
        assertThat(savedExpenseWithIdAmountDateCategoryDTO.amount()).isEqualTo(expenseWithAmountDateCategoryDTO.amount());
        assertThat(savedExpenseWithIdAmountDateCategoryDTO.transactionDate()).isEqualTo(expenseWithAmountDateCategoryDTO.transactionDate());
        assertThat(savedExpenseWithIdAmountDateCategoryDTO.expenseCategory()).isEqualTo(expenseWithAmountDateCategoryDTO.category());

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
            expenseService.addExpense("invalidUser", expenseWithAmountDateCategoryDTO);
        });

        // Verify repository calls
        verify(memberRepository, times(1)).findMemberByUsername("invalidUser");
        verify(expenseRepository, never()).save(any(Expense.class));
    }

    @DisplayName("JUnit test for ExpenseService - updateExpense()")
    @Test
    void givenValidExpenseID_whenUpdateExpense_thenReturnUpdatedExpenseResponseDTO() {
        //GIVEN
        ExpenseWithIdAmountDateCategoryDTO updatedExpenseDTO = new ExpenseWithIdAmountDateCategoryDTO(expenseId, 600.00, LocalDate.of(2025, 3, 8),
                "Transport");
        TransactionCategory updatedCategory = new TransactionCategory();
        updatedCategory.setId(2L);
        updatedCategory.setName("Transport");

        given(expenseRepository.findExpenseByPublicId(expenseId)).willReturn(Optional.of(expense));
        given(transactionCategoryRepository.getTransactionCategoryByName("Transport")).willReturn(updatedCategory);
        given(expenseRepository.save(any(Expense.class))).willReturn(expense);

        //WHEN
        ExpenseWithIdAmountDateCategoryDTO updatedExpense = expenseService.updateExpense(expenseId, updatedExpenseDTO);

        // THEN
        assertThat(updatedExpense).isNotNull();
        assertThat(updatedExpense.amount()).isEqualTo(600.00);
        assertThat(updatedExpense.transactionDate()).isEqualTo(LocalDate.of(2025, 3, 8));
        assertThat(updatedExpense.expenseCategory()).isEqualTo("Transport");

        // Verify repository calls
        verify(expenseRepository, times(1)).findExpenseByPublicId(expenseId);
        verify(transactionCategoryRepository, times(1)).getTransactionCategoryByName("Transport");
        verify(expenseRepository, times(1)).save(any(Expense.class));
    }

    @DisplayName("JUnit test for ExpenseService - updateExpense() should throw exception if expense not found")
    @Test
    void givenInvalidExpenseId_whenUpdateExpense_thenThrowException() {
        // GIVEN
        given(expenseRepository.findExpenseByPublicId(expenseId)).willReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(NoSuchElementException.class, () -> expenseService.updateExpense(expenseId, expenseWithIdAmountDateCategoryDTO));

        verify(expenseRepository, never()).save(any(Expense.class));
    }

    @DisplayName("JUnit test for ExpenseService - deleteExpenseByPublicId()")
    @Test
    void givenValidExpenseId_whenDeleteExpenseByPublicId_thenDeleteExpense() {
        // GIVEN
        given(expenseRepository.deleteExpenseByPublicId(expenseId)).willReturn(1);

        //WHEN
        int deletedRows = expenseService.deleteExpenseByPublicId(expenseId);

        //THEN
        assertThat(deletedRows).isEqualTo(1);
        verify(expenseRepository, times(1)).deleteExpenseByPublicId(expenseId);
    }

    @DisplayName("JUnit test for ExpenseService - getAllExpensesByUserName()")
    @Test
    void givenUserName_whenGetAllExpensesByUserName_thenReturnAllExpenses() {
        // GIVEN
        given(expenseRepository.getExpensesByMemberUsername("testUser")).willReturn(List.of(expense));

        // WHEN
        List<ExpenseWithIdAmountDateCategoryDTO> actualExpenses = expenseService.getAllExpensesByUsername("testUser");

        // THEN
        List<ExpenseWithIdAmountDateCategoryDTO> expectedExpenses = List.of(expenseWithIdAmountDateCategoryDTO);

        assertIterableEquals(expectedExpenses, actualExpenses);

        verify(expenseRepository, times(1)).getExpensesByMemberUsername("testUser");
    }

    @DisplayName("JUnit test for ExpenseService - getExpensesByExpenseCategoryAndMemberUsername()")
    @Test
    void givenCategoryAndUsername_whenGetExpensesByExpenseCategoryAndMemberUsername_thenReturnExpenseList() {
        //GIVEN
        given(expenseRepository.getExpensesByTransactionCategoryAndMemberUsername(category, "testUser")).willReturn(List.of(expense));

        //WHEN
        List<ExpenseWithIdAmountDateCategoryDTO> actualExpenses = expenseService.getExpensesByExpenseCategoryAndMemberUsername(category, "testUser");

        //THEN
        List<ExpenseWithIdAmountDateCategoryDTO> expectedExpenses = List.of(expenseWithIdAmountDateCategoryDTO);

        assertThat(expectedExpenses).hasSize(1);
        assertThat(expectedExpenses.get(0).expenseCategory()).isEqualTo("Food");
        assertIterableEquals(expectedExpenses, actualExpenses);

        verify(expenseRepository, times(1)).getExpensesByTransactionCategoryAndMemberUsername(category, "testUser");
    }

}
package com.codecool.eeze.service;

import com.codecool.eeze.model.dto.ReportDTO;
import com.codecool.eeze.model.dto.TotalExpenseByTransactionCategoryDTO;
import com.codecool.eeze.model.dto.reports.CategoryReport;
import com.codecool.eeze.model.dto.reports.MonthlyExpenseTotal;
import com.codecool.eeze.model.dto.reports.MonthlyIncomeExpenseReportDTO;
import com.codecool.eeze.model.dto.reports.MonthlyIncomeTotal;
import com.codecool.eeze.model.entity.Member;
import com.codecool.eeze.repository.ExpenseRepository;
import com.codecool.eeze.repository.IncomeRepository;
import com.codecool.eeze.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @Mock
    private IncomeRepository incomeRepository;

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private Member member;

    @InjectMocks
    private ReportService reportService;


    @DisplayName("JUnit test for ReportService - generateReport()")
    @Test
    void givenUsername_whenGenerateReport_thenReturnReportDTO() {
        // GIVEN
        given(incomeRepository.getTotalIncomeByMemberUsername("testUser")).willReturn(5000.00);
        given(expenseRepository.getTotalExpensesByMemberUsername("testUser")).willReturn(3000.00);

        // WHEN
        ReportDTO report = reportService.generateReport("testUser");

        // THEN
        assertThat(report).isNotNull();
        assertThat(report.totalIncome()).isEqualTo(5000.00);
        assertThat(report.totalExpenses()).isEqualTo(3000.00);
        assertThat(report.currentBalance()).isEqualTo(2000.00);

        verify(incomeRepository, times(1)).getTotalIncomeByMemberUsername("testUser");
        verify(expenseRepository, times(1)).getTotalExpensesByMemberUsername("testUser");
    }

    @DisplayName("JUnit test for ReportService - getTotalExpenseByTransactionCategory()")
    @Test
    void givenUsername_whenGetTotalExpenseByTransactionCategory_thenReturnList() {
        // GIVEN
        List<TotalExpenseByTransactionCategoryDTO> categoryExpenses = List.of(
                new TotalExpenseByTransactionCategoryDTO("Food", 1000.00, 1L),
                new TotalExpenseByTransactionCategoryDTO("Transport", 500.00, 2L)
        );
        given(expenseRepository.getExpensesByTransactionCategory("testUser")).willReturn(categoryExpenses);

        // WHEN
        List<TotalExpenseByTransactionCategoryDTO> result = reportService.getTotalExpenseByTransactionCategory("testUser");

        // THEN
        assertThat(result).hasSize(2);
        assertThat(result.get(0).categoryName()).isEqualTo("Food");
        assertThat(result.get(1).categoryName()).isEqualTo("Transport");

        verify(expenseRepository, times(1)).getExpensesByTransactionCategory("testUser");
    }

    @DisplayName("JUnit test for ReportService - getMonthlyReport()")
    @Test
    void givenUsernameMonthYear_whenGetMonthlyReport_thenReturnCategoryReportList() {
        // GIVEN
        given(memberRepository.findMemberByUsername("testUser")).willReturn(Optional.of(member));
        List<CategoryReport> reports = List.of(
                new CategoryReport("Food", 1200.00),
                new CategoryReport("Entertainment", 300.00)
        );
        given(expenseRepository.getMonthlyExpensesByCategory(member, 3, 2025)).willReturn(reports);

        // WHEN
        List<CategoryReport> result = reportService.getMonthlyReport("testUser", 3, 2025);

        // THEN
        assertThat(result).hasSize(2);
        assertThat(result.get(0).categoryName()).isEqualTo("Food");
        assertThat(result.get(1).categoryName()).isEqualTo("Entertainment");

        verify(expenseRepository, times(1)).getMonthlyExpensesByCategory(member, 3, 2025);
    }

    @DisplayName("JUnit test for ReportService - getMonthlyReport() should throw exception if member not found")
    @Test
    void givenInvalidUsername_whenGetMonthlyReport_thenThrowException() {
        // GIVEN
        given(memberRepository.findMemberByUsername("invalidUser")).willReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(NoSuchElementException.class, () -> {
            reportService.getMonthlyReport("invalidUser", 3, 2025);
        });

        verify(memberRepository, times(1)).findMemberByUsername("invalidUser");
        verify(expenseRepository, never()).getMonthlyExpensesByCategory(any(), anyInt(), anyInt());
    }

    @DisplayName("JUnit test for ReportService - getYearlyReport()")
    @Test
    void givenUsernameYear_whenGetYearlyReport_thenReturnMonthlyIncomeExpenseReportList() {
        // GIVEN
        given(memberRepository.findMemberByUsername("testUser")).willReturn(Optional.of(member));
        List<MonthlyExpenseTotal> expenseTotals = List.of(new MonthlyExpenseTotal(3, 1500.00));
        List<MonthlyIncomeTotal> incomeTotals = List.of(new MonthlyIncomeTotal(3, 2500.00));

        given(expenseRepository.getMonthlyTotalExpenses(member, 2025)).willReturn(expenseTotals);
        given(incomeRepository.getMonthlyTotalIncomes(member, 2025)).willReturn(incomeTotals);

        // WHEN
        List<MonthlyIncomeExpenseReportDTO> result = reportService.getYearlyReport("testUser", 2025);

        // THEN
        assertThat(result).hasSize(1);
        assertThat(result.get(0).month()).isEqualTo("MARCH");
        assertThat(result.get(0).totalIncome()).isEqualTo(2500.00);
        assertThat(result.get(0).totalExpense()).isEqualTo(1500.00);

        verify(expenseRepository, times(1)).getMonthlyTotalExpenses(member, 2025);
        verify(incomeRepository, times(1)).getMonthlyTotalIncomes(member, 2025);
    }

    @DisplayName("JUnit test for ReportService - getYearlyReport() should throw exception if member not found")
    @Test
    void givenInvalidUsername_whenGetYearlyReport_thenThrowException() {
        // GIVEN
        given(memberRepository.findMemberByUsername("invalidUser")).willReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(NoSuchElementException.class, () -> {
            reportService.getYearlyReport("invalidUser", 2025);
        });

        verify(memberRepository, times(1)).findMemberByUsername("invalidUser");
        verify(expenseRepository, never()).getMonthlyTotalExpenses(any(), anyInt());
        verify(incomeRepository, never()).getMonthlyTotalIncomes(any(), anyInt());
    }


}

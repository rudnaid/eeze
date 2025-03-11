package com.codecool.spendeeze.service;

import com.codecool.spendeeze.model.dto.ReportDTO;
import com.codecool.spendeeze.model.dto.TotalExpenseByTransactionCategoryDTO;
import com.codecool.spendeeze.model.dto.reports.CategoryReport;
import com.codecool.spendeeze.model.dto.reports.MonthlyExpenseTotal;
import com.codecool.spendeeze.model.dto.reports.MonthlyIncomeExpenseReportDTO;
import com.codecool.spendeeze.model.dto.reports.MonthlyIncomeTotal;
import com.codecool.spendeeze.model.entity.Member;
import com.codecool.spendeeze.repository.ExpenseRepository;
import com.codecool.spendeeze.repository.IncomeRepository;
import com.codecool.spendeeze.repository.MemberRepository;
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

    @InjectMocks
    private ReportService reportService;

    private Member member;

    @BeforeEach
    void setUp() {
        member = new Member();
        member.setId(1L);
        member.setUsername("testUser");
    }

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
                new TotalExpenseByTransactionCategoryDTO() {
                    @Override
                    public String getCategoryName() {
                        return "Food";
                    }

                    @Override
                    public double getTotalByCategory() {
                        return 1000.00;
                    }

                    @Override
                    public long getCategoryPublicId() {
                        return 1L;
                    }
                },
                new TotalExpenseByTransactionCategoryDTO() {
                    @Override
                    public String getCategoryName() {
                        return "Transport";
                    }

                    @Override
                    public double getTotalByCategory() {
                        return 500.00;
                    }

                    @Override
                    public long getCategoryPublicId() {
                        return 2L;
                    }
                }
        );
        given(expenseRepository.getExpensesByTransactionCategory("testUser")).willReturn(categoryExpenses);

        // WHEN
        List<TotalExpenseByTransactionCategoryDTO> result = reportService.getTotalExpenseByTransactionCategory("testUser");

        // THEN
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getCategoryName()).isEqualTo("Food");
        assertThat(result.get(1).getCategoryName()).isEqualTo("Transport");

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
        assertThat(result.get(0).getCategoryName()).isEqualTo("Food");
        assertThat(result.get(1).getCategoryName()).isEqualTo("Entertainment");

        verify(expenseRepository, times(1)).getMonthlyExpensesByCategory(member, 3, 2025);
    }


}

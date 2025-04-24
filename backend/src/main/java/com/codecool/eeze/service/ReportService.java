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
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.*;

@Service
public class ReportService {

    private final IncomeRepository incomeRepository;
    private final ExpenseRepository expenseRepository;
    private final MemberRepository memberRepository;

    public ReportService(IncomeRepository incomeRepository, ExpenseRepository expenseRepository, MemberRepository memberRepository) {
        this.incomeRepository = incomeRepository;
        this.expenseRepository = expenseRepository;
        this.memberRepository = memberRepository;
    }

    public ReportDTO generateReport(String username) {
        double totalIncome = incomeRepository.getTotalIncomeByMemberUsername(username);

        double totalExpenses = expenseRepository.getTotalExpensesByMemberUsername(username);

        double currentBalance = totalIncome - totalExpenses;

        return new ReportDTO(username, totalIncome, totalExpenses, currentBalance);
    }

    public List<TotalExpenseByTransactionCategoryDTO> getTotalExpenseByTransactionCategory(String memberUsername) {
        return expenseRepository.getExpensesByTransactionCategory(memberUsername);
    }

    public List<CategoryReport> getMonthlyReport(String username, int month, int year) {
        Member member = getMemberByUsername(username);

        return expenseRepository.getMonthlyExpensesByCategory(member, month, year);
    }

    public List<MonthlyIncomeExpenseReportDTO> getYearlyReport(String username, int year) {
        Member member = getMemberByUsername(username);

        List<MonthlyExpenseTotal> totalExpenses = expenseRepository.getMonthlyTotalExpenses(member, year);

        List<MonthlyIncomeTotal> totalIncomes = incomeRepository.getMonthlyTotalIncomes(member, year);

        return createMonthlyIncomeExpenseReportDTOs(totalExpenses, totalIncomes);
    }

    private List<MonthlyIncomeExpenseReportDTO> createMonthlyIncomeExpenseReportDTOs(List<MonthlyExpenseTotal> expenseTotals, List<MonthlyIncomeTotal> incomeTotals) {
        Map<Integer, MonthlyIncomeExpenseReportDTO> reportMap = new HashMap<>();

        for (MonthlyExpenseTotal expense : expenseTotals) {
            int month = expense.getMonth();

            reportMap.put(month, new MonthlyIncomeExpenseReportDTO(
                    getMonthName(month),
                    0,
                    expense.getTotalExpense()
            ));
        }

        for (MonthlyIncomeTotal income : incomeTotals) {
            int month = income.getMonth();

            reportMap.merge(month,
                    new MonthlyIncomeExpenseReportDTO(getMonthName(month), income.getTotalIncome(), 0),
                    (existing, newValue) -> new MonthlyIncomeExpenseReportDTO(
                            existing.month(),
                            existing.totalIncome() + newValue.totalIncome(),
                            existing.totalExpense()
                    ));
        }

        return new ArrayList<>(reportMap.values());
    }

    private Member getMemberByUsername(String username) {
        return memberRepository.findMemberByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("Member with username: " + username + " not found"));
    }

    private String getMonthName(int month) {
        return Month.of(month).toString();
    }
}

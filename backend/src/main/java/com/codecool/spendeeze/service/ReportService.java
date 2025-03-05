package com.codecool.spendeeze.service;

import com.codecool.spendeeze.model.dto.ReportDTO;
import com.codecool.spendeeze.model.dto.TotalExpenseByTransactionCategoryDTO;
import com.codecool.spendeeze.model.dto.reports.CategoryReport;
import com.codecool.spendeeze.model.entity.Member;
import com.codecool.spendeeze.repository.ExpenseRepository;
import com.codecool.spendeeze.repository.IncomeRepository;
import com.codecool.spendeeze.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;


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
        Member member = memberRepository.findMemberByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("Member not found"));


        return expenseRepository.getMonthlyExpenses(member, month, year);
    }
}

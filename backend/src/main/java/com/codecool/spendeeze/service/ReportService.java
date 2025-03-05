package com.codecool.spendeeze.service;

import com.codecool.spendeeze.model.dto.ReportDTO;
import com.codecool.spendeeze.model.dto.TotalExpenseByTransactionCategoryDTO;
import com.codecool.spendeeze.model.dto.reports.CategoryReport;
import com.codecool.spendeeze.model.entity.Member;
import com.codecool.spendeeze.repository.ExpenseRepository;
import com.codecool.spendeeze.repository.IncomeRepository;
import com.codecool.spendeeze.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;


@Service
public class ReportService {

    private final IncomeRepository incomeRepository;
    private final ExpenseRepository expenseRepository;
    private final MemberRepository memberRepository;

    public ReportService(IncomeRepository incomeRepository, ExpenseRepository expenseRepository, MemberService memberService, MemberRepository memberRepository) {
        this.incomeRepository = incomeRepository;
        this.expenseRepository = expenseRepository;
        this.memberRepository = memberRepository;
    }

    public ReportDTO generateReport(UUID memberPublicId) {
        double totalIncome = incomeRepository.getTotalIncomeByMemberPublicId(memberPublicId);

        double totalExpenses = expenseRepository.getTotalExpensesByMemberPublicId(memberPublicId);

        double currentBalance = totalIncome - totalExpenses;

        return new ReportDTO(memberPublicId, totalIncome, totalExpenses, currentBalance);
    }

    public List<TotalExpenseByTransactionCategoryDTO> getTotalExpenseByTransactionCategory(UUID memberPublicId) {
        return expenseRepository.getExpensesByTransactionCategory(memberPublicId);
    }

    public List<CategoryReport> getMonthlyReport(String username, int month, int year) {
        Member member = memberRepository.getMemberByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("Member not found"));


        return expenseRepository.getMonthlyExpenses(member, month, year);
    }
}

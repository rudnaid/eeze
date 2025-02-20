package com.codecool.spendeeze.repository;

import com.codecool.spendeeze.model.dto.ReportDTO;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class ReportService {

    private final IncomeRepository incomeRepository;
    private final ExpenseRepository expenseRepository;

    public ReportService(IncomeRepository incomeRepository, ExpenseRepository expenseRepository) {
        this.incomeRepository = incomeRepository;
        this.expenseRepository = expenseRepository;
    }

    public ReportDTO generateReport(UUID memberPublicId) {
        double totalIncome = incomeRepository.getTotalIncomeByMemberPublicId(memberPublicId);

        double totalExpenses = expenseRepository.getTotalExpensesByMemberPublicId(memberPublicId);

        double currentBalance = totalIncome - totalExpenses;

        return new ReportDTO(memberPublicId, totalIncome, totalExpenses, currentBalance);
    }
}

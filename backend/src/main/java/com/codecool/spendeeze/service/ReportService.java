package com.codecool.spendeeze.service;

import com.codecool.spendeeze.model.dto.ReportDTO;
import com.codecool.spendeeze.model.dto.TotalExpenseByTransactionCategoryDTO;
import com.codecool.spendeeze.repository.ExpenseRepository;
import com.codecool.spendeeze.repository.IncomeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<TotalExpenseByTransactionCategoryDTO> getTotalExpenseByTransactionCategory(UUID memberPublicId) {
        return expenseRepository.getExpensesByTransactionCategory(memberPublicId);
    }
}

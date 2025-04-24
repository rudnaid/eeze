package com.codecool.eeze.model.dto.reports;

public record MonthlyIncomeExpenseReportDTO(
        String month,
        double totalIncome,
        double totalExpense
) {
}
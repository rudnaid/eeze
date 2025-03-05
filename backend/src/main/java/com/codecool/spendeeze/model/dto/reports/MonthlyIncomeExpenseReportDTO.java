package com.codecool.spendeeze.model.dto.reports;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MonthlyIncomeExpenseReportDTO {
    private String month;
    private double totalIncome;
    private double totalExpense;
}

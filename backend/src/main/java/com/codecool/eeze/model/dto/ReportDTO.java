package com.codecool.eeze.model.dto;

public record ReportDTO(
        String memberUsername,
        double totalIncome,
        double totalExpenses,
        double currentBalance
) {
}

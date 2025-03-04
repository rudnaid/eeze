package com.codecool.spendeeze.model.dto;

public record ReportDTO(String memberUsername,
                        double totalIncome,
                        double totalExpenses,
                        double currentBalance) {
}

package com.codecool.spendeeze.model.dto;

import java.time.LocalDate;

public record ExpenseWithAmountDateCategoryDTO(
        double amount,
        LocalDate transactionDate,
        String category) {
}

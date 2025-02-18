package com.codecool.spendeeze.model.dto;

import java.time.LocalDate;

public record ExpenseResponseDTO(double amount, LocalDate transactionDate, String expenseCategory) {
}

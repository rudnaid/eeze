package com.codecool.spendeeze.model.dto;

import java.time.LocalDateTime;

public record ExpenseResponseDTO(double amount, LocalDateTime transactionDate, String expenseCategory) {
}

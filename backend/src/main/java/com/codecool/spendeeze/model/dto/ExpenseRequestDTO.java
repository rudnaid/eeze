package com.codecool.spendeeze.model.dto;

import java.time.LocalDateTime;

public record ExpenseRequestDTO(double amount, LocalDateTime transactionDate, String category) {
}

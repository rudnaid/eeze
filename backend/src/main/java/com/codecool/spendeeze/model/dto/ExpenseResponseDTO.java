package com.codecool.spendeeze.model.dto;

import java.time.LocalDate;
import java.util.UUID;

public record ExpenseResponseDTO(UUID publicId, double amount, LocalDate transactionDate, String expenseCategory) {
}

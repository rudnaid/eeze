package com.codecool.spendeeze.model.dto;

import java.time.LocalDate;

public record ExpenseRequestDTO(double amount, LocalDate transactionDate, String category) {
}

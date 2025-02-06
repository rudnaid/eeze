package com.codecool.spendeeze.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class Expense {
    private final UUID id;
    private final UUID userId;
    private final double amount;
    private final LocalDateTime transactionDate;
    private final ExpenseCategory expenseCategory;

    public Expense(UUID id, UUID userId, double amount, LocalDateTime transactionDate, ExpenseCategory expenseCategory) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.expenseCategory = expenseCategory;
    }
}

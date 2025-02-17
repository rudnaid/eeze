package com.codecool.spendeeze.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID publicId;

    @ManyToOne
    private User user;

    private double amount;
    private LocalDate transactionDate;
    private ExpenseCategory expenseCategory;
}

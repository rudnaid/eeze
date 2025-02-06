package com.codecool.spendeeze.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
public class Income {
    private int id;
    private int userId;
    private double amount;
    private LocalDate date;

    public Income(int id, int userId, double amount, LocalDate date) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.date = date;
    }

}

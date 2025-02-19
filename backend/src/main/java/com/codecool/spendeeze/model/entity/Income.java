package com.codecool.spendeeze.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Income {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    @GeneratedValue (strategy = GenerationType.UUID)
    private UUID publicId;

    @ManyToOne
    private User user;

    private double amount;
    private LocalDate date;

    public Income() {}

    public Income(int id, User user, double amount, LocalDate date) {
        this.id = id;
        this.user = user;
        this.amount = amount;
        this.date = date;
    }

}

package com.codecool.spendeeze.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class TransactionCategory {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    @OneToMany(mappedBy = "transactionCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Expense> expenses;
}

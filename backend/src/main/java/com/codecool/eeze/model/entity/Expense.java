package com.codecool.eeze.model.entity;

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

    private UUID publicId;

    @PrePersist
    public void generatePublicId() {
        this.publicId = UUID.randomUUID();
    }

    @ManyToOne
    private Member member;

    private double amount;
    private LocalDate transactionDate;

    @ManyToOne
    private TransactionCategory transactionCategory;
}

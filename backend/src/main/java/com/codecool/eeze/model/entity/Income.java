package com.codecool.eeze.model.entity;

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

    private UUID publicId;

    @PrePersist
    public void generatePublicId() {
        this.publicId = UUID.randomUUID();
    }

    @ManyToOne
    private Member member;

    private double amount;
    private LocalDate date;

    public Income() {}

    public Income(int id, Member member, double amount, LocalDate date) {
        this.id = id;
        this.member = member;
        this.amount = amount;
        this.date = date;
    }
}

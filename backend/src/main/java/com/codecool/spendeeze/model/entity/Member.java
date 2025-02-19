package com.codecool.spendeeze.model.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private UUID publicId;

    @PrePersist
    public void generatePublicId() {
        this.publicId = UUID.randomUUID();
    }

    private String firstName;
    private String lastName;
    private String country;

    @Column(unique = true)
    private String email;

    private String password;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Income> incomes;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Expense> expenses;
}

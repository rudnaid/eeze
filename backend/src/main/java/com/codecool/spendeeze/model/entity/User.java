package com.codecool.spendeeze.model.entity;


import com.codecool.spendeeze.model.Expense;
import com.codecool.spendeeze.model.Income;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID publicId;

    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Income> incomes;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Expense> expenses;
}

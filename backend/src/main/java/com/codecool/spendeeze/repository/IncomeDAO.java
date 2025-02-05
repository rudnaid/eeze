package com.codecool.spendeeze.repository;

import com.codecool.spendeeze.model.IncomeDTO;

public interface IncomeDAO {
    int addIncome(IncomeDTO income, int userId);
}

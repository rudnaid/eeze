package com.codecool.spendeeze.repository;

import com.codecool.spendeeze.model.Income;
import com.codecool.spendeeze.model.IncomeDTO;

import java.util.List;

public interface IncomeDAO {
    int addIncome(IncomeDTO income, int userId);
    List<Income> getAllIncomesByUser(int userId);

}

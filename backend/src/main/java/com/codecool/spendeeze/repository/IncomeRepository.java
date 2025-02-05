package com.codecool.spendeeze.repository;

import com.codecool.spendeeze.model.Income;
import com.codecool.spendeeze.model.IncomeDTO;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class IncomeRepository implements IncomeDAO {
    private final Map<Integer, Income> incomes;

    public IncomeRepository() {
        incomes = new HashMap<Integer, Income>();
    }

    @Override
    public int addIncome(IncomeDTO income, int userId) {
        int id = incomes.keySet().stream().max(Integer::compareTo).orElse(0) + 1;
        incomes.put(id, new Income(id, userId, income.amount(), income.date()));
        return id;
    }

    @Override
    public List<Income> getAllIncomesByUser(int userId) {
        return incomes.values().stream()
                .filter(income -> income.getUserId() == userId)
                .collect(Collectors.toList());
    }
}

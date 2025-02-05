package com.codecool.spendeeze.repository;

import com.codecool.spendeeze.model.Income;
import com.codecool.spendeeze.model.IncomeDTO;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    @Override
    public Optional<Income> getIncomeById(int incomeId) {
        return incomes.values().stream()
                .filter(income -> income.getId() == incomeId)
                .findFirst();
    }

    @Override
    public int updateIncome(int incomeId, IncomeDTO income) {
        Income oldIncome = incomes.get(incomeId);
        if (oldIncome == null) {
            return -1;
        }
        Income newIncome = new Income(incomeId, oldIncome.getUserId(), income.amount(), income.date());
        incomes.put(incomeId, newIncome);
        return 1;
    }

    @Override
    public int deleteIncome(int incomeId) {
        Income income = incomes.remove(incomeId);
        if (income == null) {
            return -1;
        }
        return income.getId();
    }
}

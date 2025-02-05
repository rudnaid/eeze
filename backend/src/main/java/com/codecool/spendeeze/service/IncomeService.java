package com.codecool.spendeeze.service;

import com.codecool.spendeeze.model.Income;
import com.codecool.spendeeze.model.IncomeDTO;
import com.codecool.spendeeze.repository.IncomeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncomeService {
    private final IncomeRepository incomeRepository;

    public IncomeService(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    public int addIncome(IncomeDTO income, int userId) {
        return incomeRepository.addIncome(income, userId);
    }
}

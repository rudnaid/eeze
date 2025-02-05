package com.codecool.spendeeze.service;

import com.codecool.spendeeze.model.Income;
import com.codecool.spendeeze.model.IncomeDTO;
import com.codecool.spendeeze.repository.IncomeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<IncomeDTO> getAllIncomesByUser(int userId) {
        List<Income> incomes = incomeRepository.getAllIncomesByUser(userId);
        List<IncomeDTO> incomeDTOs = new ArrayList<>();
        for (Income income : incomes) {
            incomeDTOs.add(new IncomeDTO(income.getAmount(), income.getDate()));
        }
        return incomeDTOs;
    }
}

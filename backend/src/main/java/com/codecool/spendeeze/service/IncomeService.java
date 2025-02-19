package com.codecool.spendeeze.service;

import com.codecool.spendeeze.model.entity.Income;
import com.codecool.spendeeze.model.dto.IncomeDTO;
import com.codecool.spendeeze.model.entity.User;
import com.codecool.spendeeze.repository.IncomeRepository;
import com.codecool.spendeeze.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class IncomeService {
    private final IncomeRepository incomeRepository;
    private final UserRepository userRepository;

    public IncomeService(IncomeRepository incomeRepository, UserRepository userRepository) {
        this.incomeRepository = incomeRepository;
        this.userRepository = userRepository;
    }

    public Income addIncome(IncomeDTO income, UUID userId) {
        User user = userRepository.getUserByPublicId(userId).orElseThrow(() -> new NoSuchElementException("User with id " + userId + " not found"));
        Income incomeEntity = convertDtoToEntity(income);
        incomeEntity.setUser(user);
        return incomeRepository.save(incomeEntity);
    }

    private Income convertDtoToEntity(IncomeDTO incomeDto) {
        Income incomeEntity = new Income();
        incomeEntity.setAmount(incomeDto.amount());
        incomeEntity.setDate(incomeDto.date());
        return incomeEntity;
    }

    public List<IncomeDTO> findIncomesByUserId(UUID userId) {
        User user = userRepository.getUserByPublicId(userId).orElseThrow(() -> new NoSuchElementException("User with id " + userId + " not found"));
        List<Income> incomes = incomeRepository.findIncomesByUser(user);
        List<IncomeDTO> incomeDTOs = new ArrayList<>();
        for (Income income : incomes) {
            incomeDTOs.add(new IncomeDTO(income.getPublicId(), income.getAmount(), income.getDate()));
        }
        return incomeDTOs;
    }

    public IncomeDTO findIncomeById(UUID incomeId) {
        Income income = incomeRepository.findIncomeByPublicId(incomeId).orElseThrow(() -> new NoSuchElementException("Income with id " + incomeId + " not found"));
        return new IncomeDTO(income.getPublicId(), income.getAmount(), income.getDate());
    }

    public IncomeDTO updateIncome(UUID incomeId, IncomeDTO updatedIncome) {
        Income income = incomeRepository.findIncomeByPublicId(incomeId).orElseThrow(() -> new NoSuchElementException("Income with id " + incomeId + " not found"));
        income.setAmount(updatedIncome.amount());
        income.setDate(updatedIncome.date());
        incomeRepository.save(income);
        return new IncomeDTO(income.getPublicId(), income.getAmount(), income.getDate());
    }

    public void deleteIncome(UUID incomeId) {
        Income income = incomeRepository.findIncomeByPublicId(incomeId).orElseThrow(() -> new NoSuchElementException("Income with id " + incomeId + " not found"));
        incomeRepository.delete(income);
    }
}

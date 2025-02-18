package com.codecool.spendeeze.service;

import com.codecool.spendeeze.model.entity.Income;
import com.codecool.spendeeze.model.IncomeDTO;
import com.codecool.spendeeze.model.entity.User;
import com.codecool.spendeeze.repository.IncomeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class IncomeService {
    private final IncomeRepository incomeRepository;
    //TODO implement UserRepository
    private final UserRepository userRepository;

    public IncomeService(IncomeRepository incomeRepository, UserRepository userRepository) {
        this.incomeRepository = incomeRepository;
        this.userRepository = userRepository;
    }

    public Income addIncome(IncomeDTO income, UUID userId) {
        User user = userRepository.findUserByPublicId(userId);
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
        User user = userRepository.findUserByPublicId(userId);
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

    public IncomeDTO updateIncome(IncomeDTO incomeDTO) {
        Income income = incomeRepository.findIncomeByPublicId(incomeDTO.publicId()).orElseThrow(() -> new NoSuchElementException("Income with id " + incomeDTO.publicId() + " not found"));
        income.setAmount(incomeDTO.amount());
        income.setDate(incomeDTO.date());
        incomeRepository.save(income);
        return new IncomeDTO(income.getPublicId(), income.getAmount(), income.getDate());
    }

    public void deleteIncome(UUID incomeId) {
        Income income = incomeRepository.findIncomeByPublicId(incomeId).orElseThrow(() -> new NoSuchElementException("Income with id " + incomeId + " not found"));
        incomeRepository.delete(income);
    }
}

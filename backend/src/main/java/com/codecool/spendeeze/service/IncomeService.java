package com.codecool.spendeeze.service;

import com.codecool.spendeeze.model.entity.Income;
import com.codecool.spendeeze.model.dto.IncomeDTO;
import com.codecool.spendeeze.model.entity.Member;
import com.codecool.spendeeze.repository.IncomeRepository;
import com.codecool.spendeeze.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class IncomeService {
    private final IncomeRepository incomeRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public IncomeService(IncomeRepository incomeRepository, MemberRepository memberRepository) {
        this.incomeRepository = incomeRepository;
        this.memberRepository = memberRepository;
    }

    public Income addIncome(IncomeDTO income, String username) {
        Member member = memberRepository.findMemberByUsername(username).orElseThrow(() -> new NoSuchElementException("User with username " + username + " not found"));
        Income incomeEntity = convertDtoToEntity(income);
        incomeEntity.setMember(member);
        return incomeRepository.save(incomeEntity);
    }

    private Income convertDtoToEntity(IncomeDTO incomeDto) {
        Income incomeEntity = new Income();
        incomeEntity.setAmount(incomeDto.amount());
        incomeEntity.setDate(incomeDto.date());
        return incomeEntity;
    }

    public List<IncomeDTO> findIncomesByMemberUsername(String username) {
        Member member = memberRepository.findMemberByUsername(username).orElseThrow(() -> new NoSuchElementException("User with username " + username + " not found"));
        List<Income> incomes = incomeRepository.findIncomesByMember(member);
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

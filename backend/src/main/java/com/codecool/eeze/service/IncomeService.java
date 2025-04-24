package com.codecool.eeze.service;

import com.codecool.eeze.model.dto.IncomeDTO;
import com.codecool.eeze.model.entity.Income;
import com.codecool.eeze.model.entity.Member;
import com.codecool.eeze.repository.IncomeRepository;
import com.codecool.eeze.repository.MemberRepository;
import jakarta.transaction.Transactional;
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

    public List<IncomeDTO> findIncomesByMemberUsername(String username) {
        Member member = findMemberByUsername(username);

        List<Income> incomes = incomeRepository.findIncomesByMember(member);

        List<IncomeDTO> incomeDTOs = new ArrayList<>();

        for (Income income : incomes) {
            incomeDTOs.add(new IncomeDTO(income.getPublicId(), income.getAmount(), income.getDate()));
        }

        return incomeDTOs;
    }

    public IncomeDTO findIncomeById(UUID incomeId) {
        Income income = findIncomeByPublicId(incomeId);

        return new IncomeDTO(income.getPublicId(), income.getAmount(), income.getDate());
    }

    public IncomeDTO addIncome(IncomeDTO income, String username) {
        Member member = findMemberByUsername(username);

        Income incomeEntity = convertDtoToEntity(income);
        incomeEntity.setMember(member);

        incomeRepository.save(incomeEntity);

        return new IncomeDTO(incomeEntity.getPublicId(), incomeEntity.getAmount(), incomeEntity.getDate());
    }

    @Transactional
    public IncomeDTO updateIncome(UUID incomeId, IncomeDTO updatedIncome) {
        Income income = findIncomeByPublicId(incomeId);

        income.setAmount(updatedIncome.amount());
        income.setDate(updatedIncome.date());

        incomeRepository.save(income);

        return new IncomeDTO(income.getPublicId(), income.getAmount(), income.getDate());
    }

    @Transactional
    public void deleteIncome(UUID incomeId) {
        Income income = findIncomeByPublicId(incomeId);

        incomeRepository.delete(income);
    }

    private Income convertDtoToEntity(IncomeDTO incomeDto) {
        Income incomeEntity = new Income();
        incomeEntity.setAmount(incomeDto.amount());
        incomeEntity.setDate(incomeDto.date());

        return incomeEntity;
    }

    private Income findIncomeByPublicId(UUID incomeId) {
        return incomeRepository.findIncomeByPublicId(incomeId)
                .orElseThrow(() -> new NoSuchElementException("Income with id " + incomeId + " not found"));
    }

    private Member findMemberByUsername(String username) {
        return memberRepository.findMemberByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User with username " + username + " not found"));
    }
}

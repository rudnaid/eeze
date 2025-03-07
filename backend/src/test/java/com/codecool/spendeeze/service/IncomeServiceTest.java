package com.codecool.spendeeze.service;

import com.codecool.spendeeze.model.dto.IncomeDTO;
import com.codecool.spendeeze.model.entity.Income;
import com.codecool.spendeeze.model.entity.Member;
import com.codecool.spendeeze.model.entity.MemberRole;
import com.codecool.spendeeze.repository.IncomeRepository;
import com.codecool.spendeeze.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class IncomeServiceTest {

    @Mock
    private IncomeRepository incomeRepository; //mocking the repository

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private IncomeService incomeService; //will create instance of the IncomeService and will inject the mock repositories

    private Member member;
    private Income income;
    private IncomeDTO incomeDTO;

    @BeforeEach
    void setUp() {

//        Set<MemberRole> memberRoles = new HashSet<>();
//        memberRoles.add(MemberRole.ROLE_USER);

        UUID incomeId = UUID.randomUUID();

        member = new Member();
        member.setId(1L);
        member.setUsername("testUsername");

        incomeDTO = new IncomeDTO(incomeId, 1000.50, LocalDate.of(2025, 3, 10));

        income = new Income();
        income.setPublicId(incomeId);
        income.setAmount(incomeDTO.amount());
        income.setDate(incomeDTO.date());
        income.setMember(member);
    }

    @DisplayName("JUnit test for IncomeService - addIncome()")
    @Test
    void givenIncomeDTOAndUsername_whenAddIncome_thenReturnSavedIncome() {

        // GIVEN
        given(memberRepository.findMemberByUsername("testUsername")).willReturn(Optional.of(member));
        given(incomeRepository.save(any(Income.class))).willReturn(income);

        // WHEN
        IncomeDTO savedIncomeDTO = incomeService.addIncome(incomeDTO, "testUsername");

        // THEN
        assertThat(savedIncomeDTO).isNotNull();

    }

    @DisplayName("JUnit test for IncomeService - findIncomesByMemberUsername()")
    @Test
    void givenUsername_whenFindIncomesByMemberUsername_thenReturnIncomeDTOList() {
        // GIVEN
        given(memberRepository.findMemberByUsername("testUsername")).willReturn(Optional.of(member));
        given(incomeRepository.findIncomesByMember(member)).willReturn(List.of(income));

        // WHEN
        List<IncomeDTO> foundIncomes = incomeService.findIncomesByMemberUsername("testUsername");

        // THEN
        assertThat(foundIncomes).isNotEmpty();
        assertThat(foundIncomes.size()).isEqualTo(1);
        assertThat(foundIncomes.get(0).amount()).isEqualTo(1000.50);
        assertThat(foundIncomes.get(0).date()).isEqualTo(LocalDate.of(2025, 3, 10));

        // Verify repository calls
        verify(memberRepository, times(1)).findMemberByUsername("testUsername");
        verify(incomeRepository, times(1)).findIncomesByMember(member);
    }

    @DisplayName("JUnit test for IncomeService - empty income list scenario")
    @Test
    void givenInvalidUsername_whenFindIncomesByMemberUsername_thenThrowException() {
        // GIVEN
        given(memberRepository.findMemberByUsername("invalidUsername")).willReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(NoSuchElementException.class, () -> {
            incomeService.findIncomesByMemberUsername("invalidUsername");
        });

        // Verify repository calls
        verify(memberRepository, times(1)).findMemberByUsername("invalidUsername");
        verify(incomeRepository, never()).findIncomesByMember(any());
    }

}

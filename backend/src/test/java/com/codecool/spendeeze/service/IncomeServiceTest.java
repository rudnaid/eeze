package com.codecool.spendeeze.service;

import com.codecool.spendeeze.model.dto.IncomeDTO;
import com.codecool.spendeeze.model.entity.Income;
import com.codecool.spendeeze.model.entity.Member;
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

        UUID incomeId = UUID.randomUUID();

        member = new Member();
        member.setId(1L);
        member.setUsername("testUsername");

        incomeDTO = new IncomeDTO(incomeId, 1000.50, LocalDate.of(2025, 3, 6));

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
        assertThat(foundIncomes.get(0).date()).isEqualTo(LocalDate.of(2025, 3, 6));

        // Verify output
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

        // Verify output
        verify(memberRepository, times(1)).findMemberByUsername("invalidUsername");
        verify(incomeRepository, never()).findIncomesByMember(any());
    }

    @DisplayName("JUnit test for IncomeService - return empty list when user has no incomes")
    @Test
    void givenUsernameWithNoIncomes_whenFindIncomesByMemberUsername_thenReturnEmptyList() {
        // GIVEN
        String username = "testUser";
        given(memberRepository.findMemberByUsername(username)).willReturn(Optional.of(member));
        given(incomeRepository.findIncomesByMember(member)).willReturn(Collections.emptyList());

        // WHEN
        List<IncomeDTO> result = incomeService.findIncomesByMemberUsername(username);

        // THEN
        assertThat(result).isEmpty();
        verify(memberRepository, times(1)).findMemberByUsername(username);
        verify(incomeRepository, times(1)).findIncomesByMember(member);
    }

    @DisplayName("JUnit test for IncomeService - findIncomeById()")
    @Test
    void givenIncomeId_whenFindIncomeById_thenReturnIncomeDTO() {
        // GIVEN
        UUID incomeId = income.getPublicId();
        given(incomeRepository.findIncomeByPublicId(incomeId)).willReturn(Optional.of(income));

        // WHEN
        IncomeDTO foundIncome = incomeService.findIncomeById(incomeId);

        // THEN
        assertThat(foundIncome).isNotNull();
        assertThat(foundIncome.amount()).isEqualTo(1000.50);
        assertThat(foundIncome.date()).isEqualTo(LocalDate.of(2025, 3, 6));

        // Verify output
        verify(incomeRepository, times(1)).findIncomeByPublicId(incomeId);
    }

    @DisplayName("JUnit test for IncomeService - findIncomeById() should throw exception if income not found")
    @Test
    void givenInvalidIncomeId_whenFindIncomeById_thenThrowException() {
        // GIVEN
        UUID invalidIncomeId = UUID.randomUUID();
        given(incomeRepository.findIncomeByPublicId(invalidIncomeId)).willReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(NoSuchElementException.class, () -> {
            incomeService.findIncomeById(invalidIncomeId);
        });

        // Verify output
        verify(incomeRepository, times(1)).findIncomeByPublicId(invalidIncomeId);
    }

    @DisplayName("JUnit test for IncomeService - updateIncome()")
    @Test
    void givenIncomeIdAndUpdatedIncome_whenUpdateIncome_thenReturnUpdatedIncomeDTO() {

        //GIVEN
        UUID incomeId = income.getPublicId();
        IncomeDTO updatedIncomeDTO = new IncomeDTO(incomeId, 2000.70, LocalDate.of(2025, 3, 6));

        given(incomeRepository.findIncomeByPublicId(incomeId)).willReturn(Optional.of(income));
        given(incomeRepository.save(any(Income.class))).willReturn(income);

        //WHEN
        IncomeDTO result = incomeService.updateIncome(incomeId, updatedIncomeDTO);

        //THEN
        assertThat(result).isNotNull();
        assertThat(result.amount()).isEqualTo(2000.70);
        assertThat(result.date()).isEqualTo(LocalDate.of(2025, 3, 6));

        //Verify output
        verify(incomeRepository, times(1)).findIncomeByPublicId(incomeId);
        verify(incomeRepository, times(1)).save(any(Income.class));

    }

    @DisplayName("JUnit test for IncomeService - updateIncome() should throw exception if income not found")
    @Test
    void givenInvalidIncomeId_whenUpdateIncome_thenThrowException() {

        //GIVEN
        UUID invalidIncomeId = UUID.randomUUID();
        IncomeDTO updatedIncomeDTO = new IncomeDTO(invalidIncomeId, 2000.70, LocalDate.of(2025, 3, 4));

        given(incomeRepository.findIncomeByPublicId(invalidIncomeId)).willReturn(Optional.empty());

        //WHEN & THEN
        assertThrows(NoSuchElementException.class, () -> {
            incomeService.updateIncome(invalidIncomeId, updatedIncomeDTO);
        });

        //Verify output
        verify(incomeRepository, times(1)).findIncomeByPublicId(invalidIncomeId);
        verify(incomeRepository, never()).save(any(Income.class));
    }

    @DisplayName("JUnit test for IncomeService - updateIncome() should update and return updated income")
    @Test
    void givenValidIncomeIdAndUpdatedIncomeDTO_whenUpdateIncome_thenReturnUpdatedIncome() {

        //GIVEN
        UUID validIncomeId = income.getPublicId();
        IncomeDTO updatedIncomeDTO = new IncomeDTO(validIncomeId, 2000.70, LocalDate.of(2025, 3, 4));

        given(incomeRepository.findIncomeByPublicId(validIncomeId)).willReturn(Optional.of(income));
        given(incomeRepository.save(any(Income.class))).willReturn(income);

        //WHEN
        IncomeDTO updatedIncome = incomeService.updateIncome(validIncomeId, updatedIncomeDTO);

        //THEN
        assertThat(updatedIncome).isNotNull();
        assertThat(updatedIncome.amount()).isEqualTo(2000.70);
        assertThat(updatedIncome.date()).isEqualTo(LocalDate.of(2025, 3, 4));

        //Verify output
        verify(incomeRepository, times(1)).findIncomeByPublicId(validIncomeId);
        verify(incomeRepository, times(1)).save(any(Income.class));
    }

    @DisplayName("JUnit test for IncomeService - deleteIncome() should remove income if found")
    @Test
    void givenValidIncomeId_whenDeleteIncome_thenIncomeShouldBeRemoved() {

        // GIVEN
        UUID validIncomeId = income.getPublicId();

        given(incomeRepository.findIncomeByPublicId(validIncomeId)).willReturn(Optional.of(income));
        doNothing().when(incomeRepository).delete(income);

        // WHEN
        incomeService.deleteIncome(validIncomeId);

        // THEN
        // Verify output
        verify(incomeRepository, times(1)).findIncomeByPublicId(validIncomeId);
        verify(incomeRepository, times(1)).delete(income);
    }

    @DisplayName("JUnit test for IncomeService - deleteIncome() should throw exception if income not found")
    @Test
    void givenInvalidIncomeId_whenDeleteIncome_thenThrowException() {

        // GIVEN
        UUID invalidIncomeId = UUID.randomUUID();
        given(incomeRepository.findIncomeByPublicId(invalidIncomeId)).willReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(NoSuchElementException.class, () -> {
            incomeService.deleteIncome(invalidIncomeId);
        });

        // Verify output
        verify(incomeRepository, times(1)).findIncomeByPublicId(invalidIncomeId);
        verify(incomeRepository, never()).delete(any(Income.class));
    }

}

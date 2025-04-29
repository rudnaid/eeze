package com.codecool.eeze.service;

import com.codecool.eeze.model.dto.IncomeDTO;
import com.codecool.eeze.model.entity.Income;
import com.codecool.eeze.model.entity.Member;
import com.codecool.eeze.repository.IncomeRepository;
import com.codecool.eeze.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IncomeServiceTest {

    @Mock
    private IncomeRepository incomeRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private Member member;

    @Mock
    private Income income;

    @InjectMocks
    private IncomeService incomeService;

    private IncomeDTO incomeDTO;
    private UUID incomeId;

    @BeforeEach
    void setUp() {
        incomeId = UUID.randomUUID();
        incomeDTO = new IncomeDTO(incomeId, 1000.50, LocalDate.of(2025, 3, 6));
    }

    @DisplayName("JUnit test for IncomeService - addIncome()")
    @Test
    void givenIncomeDTOAndUsername_whenAddIncome_thenReturnSavedIncome() {
        // GIVEN
        given(memberRepository.findMemberByUsername("testUsername")).willReturn(Optional.of(member));
        given(incomeRepository.save(any(Income.class))).willAnswer(invocation -> invocation.getArgument(0));

        // WHEN
        IncomeDTO savedIncomeDTO = incomeService.addIncome(incomeDTO, "testUsername");

        // THEN
        assertThat(savedIncomeDTO).isNotNull();
        assertThat(savedIncomeDTO.amount()).isEqualTo(1000.50);
        assertThat(savedIncomeDTO.date()).isEqualTo(LocalDate.of(2025, 3, 6));
    }

    @DisplayName("JUnit test for IncomeService - findIncomesByMemberUsername()")
    @Test
    void givenUsername_whenFindIncomesByMemberUsername_thenReturnIncomeDTOList() {
        // GIVEN
        given(memberRepository.findMemberByUsername("testUsername")).willReturn(Optional.of(member));
        given(income.getPublicId()).willReturn(incomeId);
        given(income.getAmount()).willReturn(1000.50);
        given(income.getDate()).willReturn(LocalDate.of(2025, 3, 6));
        given(incomeRepository.findIncomesByMember(member)).willReturn(List.of(income));

        // WHEN
        List<IncomeDTO> foundIncomes = incomeService.findIncomesByMemberUsername("testUsername");

        // THEN
        List<IncomeDTO> expectedIncomes = List.of(new IncomeDTO(
                income.getPublicId(),
                income.getAmount(),
                income.getDate()
        ));

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
        assertThrows(NoSuchElementException.class, () -> incomeService.findIncomesByMemberUsername("invalidUsername"));

        // Verify output
        verify(memberRepository, times(1)).findMemberByUsername("invalidUsername");
        verify(incomeRepository, never()).findIncomesByMember(any());
    }

    @DisplayName("JUnit test for IncomeService - return empty list when user has no incomes")
    @Test
    void givenUsernameWithNoIncomes_whenFindIncomesByMemberUsername_thenReturnEmptyList() {
        // GIVEN
        given(memberRepository.findMemberByUsername("testUsername")).willReturn(Optional.of(member));
        given(incomeRepository.findIncomesByMember(member)).willReturn(Collections.emptyList());

        // WHEN
        List<IncomeDTO> result = incomeService.findIncomesByMemberUsername("testUsername");

        // THEN
        assertThat(result).isEmpty();

        verify(memberRepository, times(1)).findMemberByUsername("testUsername");
        verify(incomeRepository, times(1)).findIncomesByMember(member);
    }

    @DisplayName("JUnit test for IncomeService - findIncomeById()")
    @Test
    void givenIncomeId_whenFindIncomeById_thenReturnIncomeDTO() {
        // GIVEN
        given(income.getPublicId()).willReturn(incomeId);
        given(income.getAmount()).willReturn(1000.50);
        given(income.getDate()).willReturn(LocalDate.of(2025, 3, 6));
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
        assertThrows(NoSuchElementException.class, () -> incomeService.findIncomeById(invalidIncomeId));

        verify(incomeRepository, times(1)).findIncomeByPublicId(invalidIncomeId);
    }

    @DisplayName("JUnit test for IncomeService - updateIncome()")
    @Test
    void givenIncomeIdAndUpdatedIncome_whenUpdateIncome_thenReturnUpdatedIncomeDTO() {
        //GIVEN
        IncomeDTO updatedIncomeDTO = new IncomeDTO(incomeId, 2000.70, LocalDate.of(2025, 3, 6));

        given(incomeRepository.findIncomeByPublicId(incomeId)).willReturn(Optional.of(income));
        given(incomeRepository.save(any(Income.class))).willAnswer(invocation -> invocation.getArgument(0));
        given(income.getPublicId()).willReturn(incomeId);
        given(income.getAmount()).willReturn(2000.70);
        given(income.getDate()).willReturn(LocalDate.of(2025, 3, 6));

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
        assertThrows(NoSuchElementException.class, () -> incomeService.updateIncome(invalidIncomeId, updatedIncomeDTO));

        //Verify output
        verify(incomeRepository, times(1)).findIncomeByPublicId(invalidIncomeId);
    }

    @DisplayName("JUnit test for IncomeService - updateIncome() should update and return updated income")
    @Test
    void givenValidIncomeIdAndUpdatedIncomeDTO_whenUpdateIncome_thenReturnUpdatedIncome() {
        //GIVEN
        IncomeDTO updatedIncomeDTO = new IncomeDTO(incomeId, 2000.70, LocalDate.of(2025, 3, 4));

        given(incomeRepository.findIncomeByPublicId(incomeId)).willReturn(Optional.of(income));
        given(incomeRepository.save(any(Income.class))).willAnswer(invocation -> invocation.getArgument(0));
        given(income.getPublicId()).willReturn(incomeId);
        given(income.getAmount()).willReturn(2000.70);
        given(income.getDate()).willReturn(LocalDate.of(2025, 3, 4));

        //WHEN
        IncomeDTO updatedIncome = incomeService.updateIncome(incomeId, updatedIncomeDTO);

        //THEN
        assertThat(updatedIncome).isNotNull();
        assertThat(updatedIncome.amount()).isEqualTo(2000.70);
        assertThat(updatedIncome.date()).isEqualTo(LocalDate.of(2025, 3, 4));

        //Verify output
        verify(incomeRepository, times(1)).findIncomeByPublicId(incomeId);
        verify(incomeRepository, times(1)).save(any(Income.class));
    }

    @DisplayName("JUnit test for IncomeService - deleteIncome() should remove income if found")
    @Test
    void givenValidIncomeId_whenDeleteIncome_thenIncomeShouldBeRemoved() {
        // GIVEN
        given(incomeRepository.findIncomeByPublicId(incomeId)).willReturn(Optional.of(income));

        // WHEN
        incomeService.deleteIncome(incomeId);

        // THEN
        verify(incomeRepository, times(1)).findIncomeByPublicId(incomeId);
        verify(incomeRepository, times(1)).delete(income);
    }

    @DisplayName("JUnit test for IncomeService - deleteIncome() should throw exception if income not found")
    @Test
    void givenInvalidIncomeId_whenDeleteIncome_thenThrowException() {
        // GIVEN
        UUID invalidIncomeId = UUID.randomUUID();
        given(incomeRepository.findIncomeByPublicId(invalidIncomeId)).willReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(NoSuchElementException.class, () -> incomeService.deleteIncome(invalidIncomeId));

        // Verify output
        verify(incomeRepository, times(1)).findIncomeByPublicId(invalidIncomeId);
    }

    @DisplayName("JUnit test for IncomeService - deleteIncome() should delete income when it exists")
    @Test
    void givenValidIncomeId_whenDeleteIncome_thenDeleteIncomeSuccessfully() {
        // GIVEN
        given(incomeRepository.findIncomeByPublicId(incomeId)).willReturn(Optional.of(income));

        // WHEN
        incomeService.deleteIncome(incomeId);

        // THEN
        verify(incomeRepository, times(1)).findIncomeByPublicId(incomeId);
        verify(incomeRepository, times(1)).delete(income);
    }
}

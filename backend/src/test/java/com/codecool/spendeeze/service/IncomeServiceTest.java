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

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

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

        Set<MemberRole> memberRoles = new HashSet<>();
        memberRoles.add(MemberRole.ROLE_USER);

        member = new Member();
        member.setId(1L);
        member.setUsername("testUsername");
        member.setPassword("testPassword");
        member.setFirstName("testFirstName");
        member.setLastName("testLastName");
        member.setEmail("testEmail@gmail.com");
        member.setCountry("testCountry");
        member.setRoles(memberRoles);

        income = new Income();
        income.setId(1L);
        income.setAmount(incomeDTO.amount());
        income.setDate(incomeDTO.date());
        income.setMember(member);
    }

    @DisplayName("JUnit test for addIncome method")
    @Test
    void givenIncomeDTOAndUsername_whenAddIncome_thenReturnSavedIncome() {

        //GIVEN
        String username = "testUsername";
        given(memberRepository.findMemberByUsername(username)).willReturn(Optional.of(member));
        given(incomeRepository.save(income)).willReturn(income);

        //WHEN
        Income savedIncome = incomeService.addIncome(incomeDTO, username);

        //THEN
        assertThat(savedIncome).isNotNull();
        assertThat(savedIncome).isEqualTo(incomeDTO.amount());
        assertThat(savedIncome.getDate()).isEqualTo(incomeDTO.date());
        assertThat(savedIncome.getMember().getUsername()).isEqualTo(username); //or member?
    }
}

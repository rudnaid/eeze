package com.codecool.eeze.service;

import com.codecool.eeze.model.dto.*;
import com.codecool.eeze.model.entity.Member;
import com.codecool.eeze.model.entity.MemberRole;
import com.codecool.eeze.repository.MemberRepository;
import com.codecool.eeze.security.jwt.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private Member member;

    @InjectMocks
    private MemberService memberService;

    private MemberRequestDTO memberRequestDTO;
    private LoginMemberRequestDTO loginRequestDTO;


    @BeforeEach
    void setUp() {

        memberRequestDTO = new MemberRequestDTO(
                "TestFirstName", "TestLastName", "TestCountry", "test@gmail.com", "testUser", "password123"
        );

        loginRequestDTO = new LoginMemberRequestDTO("testUser", "password123");
    }

    @DisplayName("JUnit test for MemberService - getUserResponseDTOByUsername()")
    @Test
    void givenValidUsername_whenGetUserResponseDTOByUsername_thenReturnMemberResponseDTO() {
        // GIVEN
        given(memberRepository.findMemberByUsername("testUser")).willReturn(Optional.of(member));
        given(member.getFirstName()).willReturn("TestFirstName");
        given(member.getLastName()).willReturn("TestLastName");
        given(member.getEmail()).willReturn("test@gmail.com");
        given(member.getCountry()).willReturn("TestCountry");


        // WHEN
        MemberResponseDTO result = memberService.getUserResponseDTOByUsername("testUser");

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.firstName()).isEqualTo("TestFirstName");
        assertThat(result.lastName()).isEqualTo("TestLastName");
        assertThat(result.email()).isEqualTo("test@gmail.com");
        assertThat(result.country()).isEqualTo("TestCountry");

        // Verify repository interaction
        verify(memberRepository, times(1)).findMemberByUsername("testUser");
    }

    @DisplayName("JUnit test for MemberService - getUserResponseDTOByUsername() should throw exception if user not found")
    @Test
    void givenInvalidUsername_whenGetUserResponseDTOByUsername_thenThrowException() {
        // GIVEN
        given(memberRepository.findMemberByUsername("invalidUser")).willReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(NoSuchElementException.class, () -> {
            memberService.getUserResponseDTOByUsername("invalidUser");
        });

        // Verify repository interaction
        verify(memberRepository, times(1)).findMemberByUsername("invalidUser");
    }

    @DisplayName("JUnit test for MemberService - addMember()")
    @Test
    void givenMemberRequestDTO_whenAddMember_thenReturnMemberResponseDTO() {
        // GIVEN
        given(passwordEncoder.encode(anyString())).willReturn("encodedPassword");
        given(memberRepository.save(any(Member.class))).willAnswer(invocation -> invocation.getArgument(0));

        // WHEN
        MemberResponseDTO savedMember = memberService.addMember(memberRequestDTO);

        // THEN
        assertThat(savedMember).isNotNull();
        assertThat(savedMember.firstName()).isEqualTo("TestFirstName");
        assertThat(savedMember.lastName()).isEqualTo("TestLastName");
        assertThat(savedMember.country()).isEqualTo("TestCountry");
        assertThat(savedMember.email()).isEqualTo("test@gmail.com");

        // Verify repository interactions
        verify(passwordEncoder, times(1)).encode(anyString());
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @DisplayName("JUnit test for MemberService - updateMember()")
    @Test
    void givenMemberRequestDTO_whenUpdateMember_thenReturnUpdatedMemberResponseDTO() {
        // GIVEN
        given(memberRepository.findMemberByUsername("testUser")).willReturn(Optional.of(member));
        given(memberRepository.save(any(Member.class))).willReturn(member);
        given(member.getFirstName()).willReturn("TestFirstName");
        given(member.getLastName()).willReturn("TestLastName");
        given(member.getEmail()).willReturn("updated@gmail.com");
        given(member.getCountry()).willReturn("UpdatedCountry");

        MemberUpdateDTO memberUpdateDTO = new MemberUpdateDTO(
                Optional.of(""), Optional.of(""), Optional.of("UpdatedCountry"), Optional.of("updated@gmail.com"), Optional.of("")
        );
        // WHEN
        MemberResponseDTO updatedMember = memberService.updateMember("testUser", memberUpdateDTO);

        // THEN
        assertThat(updatedMember).isNotNull();
        assertThat(updatedMember.email()).isEqualTo("updated@gmail.com");
        assertThat(updatedMember.country()).isEqualTo("UpdatedCountry");

        // Verify repository interactions
        verify(memberRepository, times(1)).findMemberByUsername("testUser");
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @DisplayName("JUnit test for MemberService - deleteMemberByUsername()")
    @Test
    void givenValidUsername_whenDeleteMemberByUsername_thenReturnSuccess() {
        // GIVEN
        given(memberRepository.deleteMemberByUsername("testUser")).willReturn(1);

        // WHEN
        int deletedRows = memberService.deleteMemberByUsername("testUser");

        // THEN
        assertThat(deletedRows).isEqualTo(1);

        // Verify repository interaction
        verify(memberRepository, times(1)).deleteMemberByUsername("testUser");
    }

    @DisplayName("JUnit test for MemberService - deleteMemberByUsername() should return 0 when user does not exist")
    @Test
    void givenInvalidUsername_whenDeleteMemberByUsername_thenReturnZero() {
        // GIVEN
        given(memberRepository.deleteMemberByUsername("invalidUser")).willReturn(0);

        // WHEN
        int deletedRows = memberService.deleteMemberByUsername("invalidUser");

        // THEN
        assertThat(deletedRows).isEqualTo(0);

        // Verify repository interaction
        verify(memberRepository, times(1)).deleteMemberByUsername("invalidUser");
    }
}
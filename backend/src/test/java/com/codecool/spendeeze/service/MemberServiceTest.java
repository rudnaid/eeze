package com.codecool.spendeeze.service;

import com.codecool.spendeeze.model.dto.JwtResponse;
import com.codecool.spendeeze.model.dto.LoginMemberRequestDTO;
import com.codecool.spendeeze.model.dto.MemberRequestDTO;
import com.codecool.spendeeze.model.dto.MemberResponseDTO;
import com.codecool.spendeeze.model.entity.Member;
import com.codecool.spendeeze.model.entity.MemberRole;
import com.codecool.spendeeze.repository.MemberRepository;
import com.codecool.spendeeze.security.jwt.JwtUtils;
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

    @InjectMocks
    private MemberService memberService;

    private Member member;
    private MemberRequestDTO memberRequestDTO;
    private LoginMemberRequestDTO loginRequestDTO;

    @BeforeEach
    void setUp() {
        member = new Member();
        member.setId(1L);
        member.setUsername("testUser");
        member.setPassword("testPassword");
        member.setFirstName("TestFirstName");
        member.setLastName("TestLastName");
        member.setEmail("test@gmail.com");
        member.setCountry("TestCountry");
        member.setRoles(Set.of(MemberRole.ROLE_USER));

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

        // WHEN
        MemberResponseDTO result = memberService.getUserResponseDTOByUsername("testUser");

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.firstName()).isEqualTo(member.getFirstName());
        assertThat(result.lastName()).isEqualTo(member.getLastName());
        assertThat(result.email()).isEqualTo(member.getEmail());
        assertThat(result.country()).isEqualTo(member.getCountry());

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
        given(memberRepository.save(any(Member.class))).willReturn(member);

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

    @DisplayName("JUnit test for MemberService - authenticateUser()")
    @Test
    void givenLoginRequestDTO_whenAuthenticateUser_thenReturnJwtResponse() {
        // GIVEN
        Authentication authentication = mock(Authentication.class);
        User userDetails = new User("testUser", "encodedPassword", List.of(() -> "ROLE_USER"));

        given(authenticationManager.authenticate(any())).willReturn(authentication);
        given(authentication.getPrincipal()).willReturn(userDetails);
        given(jwtUtils.generateJwtToken(authentication)).willReturn("mocked-jwt-token");

        // WHEN
        JwtResponse jwtResponse = memberService.authenticateUser(loginRequestDTO);

        // THEN
        assertThat(jwtResponse).isNotNull();
        assertThat(jwtResponse.jwt()).isEqualTo("mocked-jwt-token");
        assertThat(jwtResponse.username()).isEqualTo("testUser");
        assertThat(jwtResponse.roles()).contains("ROLE_USER");

        // Verify interactions
        verify(authenticationManager, times(1)).authenticate(any());
        verify(jwtUtils, times(1)).generateJwtToken(authentication);
    }

    @DisplayName("JUnit test for MemberService - updateMember()")
    @Test
    void givenMemberRequestDTO_whenUpdateMember_thenReturnUpdatedMemberResponseDTO() {
        // GIVEN
        given(memberRepository.findMemberByUsername("testUser")).willReturn(Optional.of(member));
        given(memberRepository.save(any(Member.class))).willReturn(member);
        member.setEmail("updated@gmail.com");
        member.setCountry("UpdatedCountry");
        memberRequestDTO = new MemberRequestDTO("TestFirstName", "TestLastName", "UpdatedCountry", "updated@gmail.com", "testUser", "password123");

        // WHEN
        MemberResponseDTO updatedMember = memberService.updateMember(memberRequestDTO);

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
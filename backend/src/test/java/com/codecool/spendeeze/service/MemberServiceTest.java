package com.codecool.spendeeze.service;

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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
}
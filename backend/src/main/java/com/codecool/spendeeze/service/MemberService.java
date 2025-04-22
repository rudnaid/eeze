package com.codecool.spendeeze.service;

import com.codecool.spendeeze.model.dto.*;
import com.codecool.spendeeze.model.entity.Member;
import com.codecool.spendeeze.model.entity.MemberRole;
import com.codecool.spendeeze.repository.MemberRepository;
import com.codecool.spendeeze.security.jwt.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Autowired
    public MemberService(MemberRepository memberRepository, PasswordEncoder encoder, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.memberRepository = memberRepository;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    public MemberResponseDTO getUserResponseDTOByUsername(String username) {
        Optional<Member> member = memberRepository.findMemberByUsername(username);
        return member.map(this::convertToMemberResponseDTO).orElseThrow(NoSuchElementException::new);
    }

    private MemberResponseDTO convertToMemberResponseDTO(Member member) {
        return new MemberResponseDTO(
                member.getFirstName(),
                member.getLastName(),
                member.getCountry(),
                member.getEmail()
        );
    }

    private Member convertToMember(MemberRequestDTO memberRequestDTO) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Member member = objectMapper.convertValue(memberRequestDTO, Member.class);
            member.setRoles(Set.of(MemberRole.ROLE_USER));
            member.setPassword(encoder.encode(member.getPassword()));
            return member;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public MemberResponseDTO addMember(MemberRequestDTO memberRequestDTO) {
        if (memberRepository.findMemberByUsername(memberRequestDTO.username()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exist!");
        }

        Member member = convertToMember(memberRequestDTO);

        memberRepository.save(member);

        return convertToMemberResponseDTO(member);
    }

    private Member getUserByUsername(String username) {
        Optional<Member> member = memberRepository.findMemberByUsername(username);
        return member.orElseThrow(NoSuchElementException::new);
    }

    public MemberResponseDTO updateMember(String username, MemberUpdateDTO memberUpdateDTO) {
        Member member = getUserByUsername(username);

        memberUpdateDTO.firstName().ifPresent(member::setFirstName);
        memberUpdateDTO.lastName().ifPresent(member::setLastName);
        memberUpdateDTO.country().ifPresent(member::setCountry);
        memberUpdateDTO.email().ifPresent(member::setEmail);
        memberUpdateDTO.password().ifPresent(password -> member.setPassword(encoder.encode(password)));

        memberRepository.save(member);

        return convertToMemberResponseDTO(member);
    }

    public int deleteMemberByUsername(String username) {
        return memberRepository.deleteMemberByUsername(username);
    }

    public JwtResponse authenticateUser(LoginMemberRequestDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        User userDetails = (User)authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();
        return new JwtResponse(jwt, userDetails.getUsername(), roles);
    }
}

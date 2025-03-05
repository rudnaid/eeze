package com.codecool.spendeeze.controller;

import com.codecool.spendeeze.model.dto.JwtResponse;
import com.codecool.spendeeze.model.dto.LoginMemberRequestDTO;
import com.codecool.spendeeze.model.dto.MemberRequestDTO;
import com.codecool.spendeeze.model.dto.MemberResponseDTO;
import com.codecool.spendeeze.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/me")
    public MemberResponseDTO getUser(@RequestParam("username") String username) {
        return memberService.getUserResponseDTOByUsername(username);
    }

    @PostMapping("/register")
    public MemberResponseDTO createMember(@RequestBody MemberRequestDTO memberRequestDTO) {
        return memberService.addMember(memberRequestDTO);
    }

    @PostMapping("/login")
    public JwtResponse authenticateUser(@RequestBody LoginMemberRequestDTO loginRequest) {
        return memberService.authenticateUser(loginRequest);
    }

    @PutMapping
    public MemberResponseDTO updateMember(@RequestBody MemberRequestDTO memberRequestDTO) {
        return memberService.updateMember(memberRequestDTO);
    }

    @DeleteMapping
    public int deleteMember(@RequestParam String username) {
        return memberService.deleteMemberByUsername(username);
    }
}

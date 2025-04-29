package com.codecool.eeze.controller;

import com.codecool.eeze.model.dto.*;
import com.codecool.eeze.service.MemberService;
import com.codecool.eeze.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class MemberController {
    private final MemberService memberService;
    private final AuthUtil authUtil;

    @Autowired
    public MemberController(MemberService memberService, AuthUtil authUtil) {
        this.memberService = memberService;
        this.authUtil = authUtil;
    }

    @GetMapping("/me")
    public MemberResponseDTO getUser() {
        String username = authUtil.getUsername();
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

    @PatchMapping
    public MemberResponseDTO updateMember(@RequestBody MemberUpdateDTO memberUpdateDTO) {
        String username = authUtil.getUsername();
        return memberService.updateMember(username, memberUpdateDTO);
    }

    @DeleteMapping
    public int deleteMember() {
        String username = authUtil.getUsername();
        return memberService.deleteMemberByUsername(username);
    }
}

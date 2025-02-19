package com.codecool.spendeeze.controller;

import com.codecool.spendeeze.model.dto.MemberRequestDTO;
import com.codecool.spendeeze.model.dto.MemberResponseDTO;
import com.codecool.spendeeze.service.MemberService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/me")
    public MemberResponseDTO getUser(@RequestParam("memberPublicId") UUID memberPublicId) {
        return memberService.getUserResponseDTOByPublicId(memberPublicId);
    }

    @PostMapping("/register")
    public MemberResponseDTO createMember(@RequestBody MemberRequestDTO memberRequestDTO) {
        return memberService.addMember(memberRequestDTO);
    }

    @PutMapping
    public MemberResponseDTO updateMember(@RequestParam UUID memberPublicId, @RequestBody MemberRequestDTO memberRequestDTO) {
        return memberService.updateMember(memberPublicId, memberRequestDTO);
    }

    @DeleteMapping
    public int deleteMember(@RequestParam UUID memberPublicId) {
        return memberService.deleteMemberByPublicId(memberPublicId);
    }
}

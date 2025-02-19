package com.codecool.spendeeze.service;

import com.codecool.spendeeze.model.dto.MemberRequestDTO;
import com.codecool.spendeeze.model.dto.MemberResponseDTO;
import com.codecool.spendeeze.model.entity.Member;
import com.codecool.spendeeze.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberResponseDTO getUserResponseDTOByPublicId(UUID memberPublicId) {
        Optional<Member> member = memberRepository.getMemberByPublicId(memberPublicId);
        return member.map(this::convertToUserResponseDTO).orElseThrow(NoSuchElementException::new);
    }

    private MemberResponseDTO convertToUserResponseDTO(Member member) {
        return new MemberResponseDTO(
                member.getPublicId(),
                member.getFirstName(),
                member.getLastName(),
                member.getCountry(),
                member.getEmail()
        );
    }

    private Member convertToUser(MemberRequestDTO memberRequestDTO) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.convertValue(memberRequestDTO, Member.class);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public MemberResponseDTO addMember(MemberRequestDTO memberRequestDTO) {
        Member member = convertToUser(memberRequestDTO);

        memberRepository.save(member);

        return convertToUserResponseDTO(member);
    }

    private Member getUserByPublicId(UUID memberPublicId) {
        Optional<Member> member = memberRepository.getMemberByPublicId(memberPublicId);
        return member.orElseThrow(NoSuchElementException::new);
    }

    public MemberResponseDTO updateMember(UUID memberPublicId, MemberRequestDTO memberRequestDTO) {
        Member member = getUserByPublicId(memberPublicId);

        member.setFirstName(memberRequestDTO.firstName());
        member.setLastName(memberRequestDTO.lastName());
        member.setEmail(memberRequestDTO.email());
        member.setCountry(memberRequestDTO.country());

        memberRepository.save(member);

        return convertToUserResponseDTO(member);
    }

    public int deleteMemberByPublicId(UUID memberPublicId) {
        return memberRepository.deleteMemberByPublicId(memberPublicId);
    }
}

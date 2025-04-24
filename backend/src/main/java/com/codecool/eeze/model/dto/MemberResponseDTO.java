package com.codecool.eeze.model.dto;

public record MemberResponseDTO(
        String firstName,
        String lastName,
        String country,
        String email
) {
}

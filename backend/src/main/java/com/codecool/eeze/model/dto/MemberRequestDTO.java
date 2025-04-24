package com.codecool.eeze.model.dto;

public record MemberRequestDTO(
        String firstName,
        String lastName,
        String country,
        String email,
        String username,
        String password
) {
}

package com.codecool.spendeeze.model.dto;

import java.util.UUID;

public record MemberResponseDTO(UUID publicId, String firstName, String lastName, String country, String email) {
}

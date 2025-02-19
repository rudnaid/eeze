package com.codecool.spendeeze.model.dto;

import java.util.UUID;

public record UserResponseDTO(UUID publicId, String firstName, String lastName, String username, String email) {
}

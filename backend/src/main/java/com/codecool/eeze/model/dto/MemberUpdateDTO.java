package com.codecool.eeze.model.dto;

import java.util.Optional;

public record MemberUpdateDTO(
        Optional<String> firstName,
        Optional<String> lastName,
        Optional<String> country,
        Optional<String> email,
        Optional<String> password
) {
}

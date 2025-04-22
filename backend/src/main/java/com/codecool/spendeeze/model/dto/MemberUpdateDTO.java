package com.codecool.spendeeze.model.dto;

import java.util.Optional;

public record MemberUpdateDTO(Optional<String> firstName, Optional<String> lastName, Optional<String> country, Optional<String> email, Optional<String> password) {
}

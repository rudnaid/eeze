package com.codecool.eeze.model.dto;

import java.util.List;

public record JwtResponse(
        String jwt,
        String username,
        List<String> roles
) {
}

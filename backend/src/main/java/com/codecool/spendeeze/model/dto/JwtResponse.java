package com.codecool.spendeeze.model.dto;

import java.util.List;

public record JwtResponse(String jwt, String username, List<String> roles) {
}

package com.codecool.spendeeze.model.dto;

import java.time.LocalDate;
import java.util.UUID;

public record IncomeDTO(UUID memberPublicId, double amount, LocalDate date) {}

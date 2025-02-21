package com.codecool.spendeeze.model.dto;

import java.util.UUID;

public record ReportDTO(UUID memberPublicId,
                        double totalIncome,
                        double totalExpenses,
                        double currentBalance) {
}

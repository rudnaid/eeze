package com.codecool.eeze.model.dto;

public record TotalExpenseByTransactionCategoryDTO(
        String categoryName,
        double totalByCategory,
        long categoryPublicId
) {}

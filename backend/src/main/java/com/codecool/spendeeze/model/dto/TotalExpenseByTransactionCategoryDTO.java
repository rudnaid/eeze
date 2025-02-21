package com.codecool.spendeeze.model.dto;

public interface TotalExpenseByTransactionCategoryDTO {

    String getCategoryName();
    double getTotalByCategory();
    long getCategoryPublicId();
}

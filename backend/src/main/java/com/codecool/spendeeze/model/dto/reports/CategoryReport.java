package com.codecool.spendeeze.model.dto.reports;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryReport {
    private String categoryName;
    private double totalAmount;
}

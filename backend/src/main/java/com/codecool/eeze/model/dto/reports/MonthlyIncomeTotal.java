package com.codecool.eeze.model.dto.reports;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MonthlyIncomeTotal {

    int month;
    double totalIncome;
}

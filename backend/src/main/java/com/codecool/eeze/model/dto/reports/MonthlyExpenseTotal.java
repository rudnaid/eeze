package com.codecool.eeze.model.dto.reports;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MonthlyExpenseTotal {

    int month;
    double totalExpense;
}

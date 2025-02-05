package com.codecool.spendeeze.controller;

import com.codecool.spendeeze.model.IncomeDTO;
import com.codecool.spendeeze.service.IncomeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/income")
public class IncomeController {
    private final IncomeService incomeService;

    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @PostMapping("/")
    public int addIncome(@RequestBody IncomeDTO income, @RequestParam int userId){
        return incomeService.addIncome(income, userId);
    }

    @GetMapping("/")
    public List<IncomeDTO> getAllIncomeByUserId(@RequestParam int userId){
        return incomeService.getAllIncomesByUser(userId);
    }
}

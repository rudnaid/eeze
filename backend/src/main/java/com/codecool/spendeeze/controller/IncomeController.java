package com.codecool.spendeeze.controller;

import com.codecool.spendeeze.model.IncomeDTO;
import com.codecool.spendeeze.model.entity.Income;
import com.codecool.spendeeze.service.IncomeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/income")
public class IncomeController {
    private final IncomeService incomeService;

    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @PostMapping("/")
    public Income addIncome(@RequestBody IncomeDTO income, @RequestParam UUID userId){
        return incomeService.addIncome(income, userId);
    }

    @GetMapping("/")
    public List<IncomeDTO> findIncomesByUser(@RequestParam UUID userId){
        return incomeService.findIncomesByUserId(userId);
    }

    @GetMapping("/{incomeId}")
    public IncomeDTO findIncomeById(@PathVariable UUID incomeId){
        return incomeService.findIncomeById(incomeId);
    }

    @PutMapping("/{incomeId}")
    public IncomeDTO updateIncome(@RequestBody IncomeDTO incomeDTO){
        return incomeService.updateIncome(incomeDTO);
    }

    @DeleteMapping("/{incomeId}")
    public void deleteIncome(@PathVariable UUID incomeId){
        incomeService.deleteIncome(incomeId);
    }
}

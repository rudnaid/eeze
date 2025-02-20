package com.codecool.spendeeze.controller;

import com.codecool.spendeeze.model.dto.IncomeDTO;
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
    public Income addIncome(@RequestBody IncomeDTO income, @RequestParam UUID memberPublicId){
        return incomeService.addIncome(income, memberPublicId);
    }

    @GetMapping("/")
    public List<IncomeDTO> findIncomesByMember(@RequestParam UUID memberPublicId){
        return incomeService.findIncomesByMemberId(memberPublicId);
    }

    @GetMapping("/{incomeId}")
    public IncomeDTO findIncomeById(@PathVariable UUID incomeId){
        return incomeService.findIncomeById(incomeId);
    }

    @PutMapping("/{incomeId}")
    public IncomeDTO updateIncome(@PathVariable UUID incomeId, @RequestBody IncomeDTO updatedIncome){
        return incomeService.updateIncome(incomeId, updatedIncome);
    }

    @DeleteMapping("/{incomeId}")
    public void deleteIncome(@PathVariable UUID incomeId){
        incomeService.deleteIncome(incomeId);
    }
}

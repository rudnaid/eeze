package com.codecool.spendeeze.controller;

import com.codecool.spendeeze.model.dto.IncomeDTO;
import com.codecool.spendeeze.model.entity.Income;
import com.codecool.spendeeze.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/incomes")
public class IncomeController {
    private final IncomeService incomeService;

    @Autowired
    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @PostMapping
    public IncomeDTO addIncome(@RequestBody IncomeDTO income){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return incomeService.addIncome(income, username);
    }

    @GetMapping
    public List<IncomeDTO> findIncomesByMember(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return incomeService.findIncomesByMemberUsername(username);
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

package com.codecool.spendeeze.controller;

import com.codecool.spendeeze.model.dto.IncomeDTO;
import com.codecool.spendeeze.service.IncomeService;
import com.codecool.spendeeze.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/incomes")
public class IncomeController {
    private final IncomeService incomeService;
    private final AuthUtil authUtil;

    @Autowired
    public IncomeController(IncomeService incomeService, AuthUtil authUtil) {
        this.incomeService = incomeService;
        this.authUtil = authUtil;
    }

    @PostMapping
    public IncomeDTO addIncome(@RequestBody IncomeDTO income){
        String username = authUtil.getUsername();
        return incomeService.addIncome(income, username);
    }

    @GetMapping
    public List<IncomeDTO> findIncomesByMember(){
        String username = authUtil.getUsername();
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

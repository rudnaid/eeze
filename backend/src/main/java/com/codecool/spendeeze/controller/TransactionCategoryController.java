package com.codecool.spendeeze.controller;

import com.codecool.spendeeze.model.entity.TransactionCategory;
import com.codecool.spendeeze.service.TransactionCategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class TransactionCategoryController {
    private final TransactionCategoryService transactionCategoryService;

    public TransactionCategoryController(TransactionCategoryService transactionCategoryService) {
        this.transactionCategoryService = transactionCategoryService;
    }

    @GetMapping
    public List<TransactionCategory> getAllTransactionCategories() {
        return transactionCategoryService.getAllTransactionCategories();
    }

}

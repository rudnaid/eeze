package com.codecool.eeze.controller;

import com.codecool.eeze.model.dto.TransactionCategoryDTO;
import com.codecool.eeze.service.TransactionCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class TransactionCategoryController {
    private final TransactionCategoryService transactionCategoryService;

    @Autowired
    public TransactionCategoryController(TransactionCategoryService transactionCategoryService) {
        this.transactionCategoryService = transactionCategoryService;
    }

    @GetMapping
    public List<TransactionCategoryDTO> getAllTransactionCategories() {
        return transactionCategoryService.getAllTransactionCategoryDTOs();
    }
}

package com.codecool.spendeeze.service;

import com.codecool.spendeeze.model.dto.TransactionCategoryDTO;
import com.codecool.spendeeze.model.entity.TransactionCategory;
import com.codecool.spendeeze.repository.TransactionCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionCategoryService {
    private final TransactionCategoryRepository transactionCategoryRepository;

    public TransactionCategoryService(TransactionCategoryRepository transactionCategoryRepository) {
        this.transactionCategoryRepository = transactionCategoryRepository;
    }

    public List<TransactionCategoryDTO> getAllTransactionCategoryDTOs() {
        List<TransactionCategory> transactionCategories = transactionCategoryRepository.findAll();
        
        return transactionCategories.stream()
                .map(transactionCategory -> new TransactionCategoryDTO(transactionCategory.getName()))
                .toList();
    }

}

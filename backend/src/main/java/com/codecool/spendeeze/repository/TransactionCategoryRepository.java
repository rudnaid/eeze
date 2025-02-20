package com.codecool.spendeeze.repository;

import com.codecool.spendeeze.model.entity.TransactionCategory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionCategoryRepository {
    TransactionCategory getTransactionCategoryByName(String name);
    List<TransactionCategory> getAllTransactionCategories();
    TransactionCategory findByName(String category);
}

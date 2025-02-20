package com.codecool.spendeeze.repository;

import com.codecool.spendeeze.model.entity.TransactionCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionCategoryRepository extends JpaRepository<TransactionCategory, Long> {
    TransactionCategory getTransactionCategoryByName(String name);
    List<TransactionCategory> getAllTransactionCategories();
    TransactionCategory findByName(String category);
}

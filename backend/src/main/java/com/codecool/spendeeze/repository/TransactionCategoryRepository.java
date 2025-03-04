package com.codecool.spendeeze.repository;

import com.codecool.spendeeze.model.entity.TransactionCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionCategoryRepository extends JpaRepository<TransactionCategory, Long> {
    TransactionCategory getTransactionCategoryByName(String name);
}

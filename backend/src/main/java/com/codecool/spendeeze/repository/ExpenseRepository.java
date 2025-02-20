package com.codecool.spendeeze.repository;

import com.codecool.spendeeze.model.entity.Expense;
import com.codecool.spendeeze.model.entity.TransactionCategory;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    Optional<Expense> findExpenseByPublicId(UUID id);

    @Transactional
    @Modifying
    @Query("DELETE FROM Expense e WHERE e.publicId = :publicId")
    int deleteExpenseByPublicId(@Param("publicId") UUID publicId);

    List<Expense> getExpensesByMemberPublicId(UUID userId);

    List<Expense> getExpensesByTransactionCategoryAndMemberPublicId(TransactionCategory category, UUID userPublicId);

    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.member.publicId = :memberPublicId")
    Optional<Double> getTotalExpensesByMemberPublicId(@Param("memberPublicId") UUID memberPublicId);

}

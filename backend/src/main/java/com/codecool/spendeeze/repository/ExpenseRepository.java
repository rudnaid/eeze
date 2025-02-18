package com.codecool.spendeeze.repository;

import com.codecool.spendeeze.model.ExpenseCategory;
import com.codecool.spendeeze.model.dto.ExpenseResponseDTO;
import com.codecool.spendeeze.model.entity.Expense;
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
    UUID deleteExpenseByPublicId(@Param("publicId") UUID publicId);

    List<ExpenseResponseDTO> getExpensesByUserPublicId(UUID userId);

    List<ExpenseResponseDTO> getExpensesByExpenseCategoryAndUserPublicId(ExpenseCategory category, UUID userPublicId);
}

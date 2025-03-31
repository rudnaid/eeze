package com.codecool.spendeeze.repository;

import com.codecool.spendeeze.model.dto.TotalExpenseByTransactionCategoryDTO;
import com.codecool.spendeeze.model.dto.reports.CategoryReport;
import com.codecool.spendeeze.model.dto.reports.MonthlyExpenseTotal;
import com.codecool.spendeeze.model.dto.reports.MonthlyIncomeExpenseReportDTO;
import com.codecool.spendeeze.model.entity.Expense;
import com.codecool.spendeeze.model.entity.Member;
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

    List<Expense> getExpensesByMemberUsername(String username);

    List<Expense> getExpensesByTransactionCategoryAndMemberUsername(TransactionCategory category, String username);

    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.member.username = :username")
    double getTotalExpensesByMemberUsername(@Param("username") String username);

    @Query("SELECT SUM(e.amount) as totalByCategory, e.transactionCategory.name as categoryName, e.transactionCategory.id as categoryPublicId FROM Expense e WHERE e.member.username = :username GROUP BY e.transactionCategory.id, e.transactionCategory.name")
    List<TotalExpenseByTransactionCategoryDTO> getExpensesByTransactionCategory(String username);

    @Query("SELECT new com.codecool.spendeeze.model.dto.reports.CategoryReport(tc.name, ROUND(SUM(e.amount), 2)) " +
            "FROM Expense e " +
            "JOIN e.transactionCategory tc " +
            "WHERE e.member = :member " +
            "AND FUNCTION('DATE_PART', 'month', e.transactionDate) = :month " +
            "AND FUNCTION('DATE_PART', 'year', e.transactionDate) = :year " +
            "GROUP BY tc.name")

    List<CategoryReport> getMonthlyExpensesByCategory(@Param("member") Member member, @Param("month") int month, @Param("year") int year);

    @Query("SELECT new com.codecool.spendeeze.model.dto.reports.MonthlyExpenseTotal(" +
            "EXTRACT(MONTH FROM e.transactionDate) AS month, " +
            "ROUND(SUM(e.amount), 2)) AS expenseTotal " +
            "FROM Expense e " +
            "WHERE e.member = :member " +
            "AND EXTRACT(YEAR FROM e.transactionDate) = :year " +
            "GROUP BY EXTRACT(MONTH FROM e.transactionDate) " +
            "ORDER BY EXTRACT(MONTH FROM e.transactionDate)")

    List<MonthlyExpenseTotal> getMonthlyTotalExpenses(@Param("member") Member member, @Param("year") int year);


    @Query("SELECT COALESCE(SUM (e.amount), 0) FROM Expense e WHERE e.member.username = :username AND MONTH(e.transactionDate) = :month AND YEAR(e.transactionDate) = :year ORDER BY e.transactionDate DESC")
    List<Expense> getMonthlyExpensesByUsername(@Param("username") String username, @Param("month") int month, @Param("year") int year);
}

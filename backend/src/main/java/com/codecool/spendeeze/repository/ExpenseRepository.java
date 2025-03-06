package com.codecool.spendeeze.repository;

import com.codecool.spendeeze.model.dto.TotalExpenseByTransactionCategoryDTO;
import com.codecool.spendeeze.model.dto.reports.CategoryReport;
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

    @Query("SELECT new com.codecool.spendeeze.model.dto.reports.CategoryReport(tc.name, SUM(e.amount)) " +
            "FROM Expense e " +
            "JOIN e.transactionCategory tc " +
            "WHERE e.member = :member " +
            "AND FUNCTION('DATE_PART', 'month', e.transactionDate) = :month " +
            "AND FUNCTION('DATE_PART', 'year', e.transactionDate) = :year " +
            "GROUP BY tc.name")

    List<CategoryReport> getMonthlyExpensesByCategory(@Param("member") Member member, @Param("month") int month, @Param("year") int year);

    @Query("SELECT new com.codecool.spendeeze.model.dto.reports.MonthlyIncomeExpenseReportDTO(" +
            "   TO_CHAR(expenseSub.month, 'FMMonth'), " +
            "   incomeSub.monthlyIncome, " +
            "   expenseSub.monthlyExpense" +
            ") " +
            "FROM (SELECT e.member AS member, TO_CHAR(e.transactionDate, 'YYYY-MM') AS month, SUM(e.amount) AS monthlyExpense FROM Expense e WHERE e.member = :member AND FUNCTION('DATE_PART', 'year', e.transactionDate) = :year GROUP BY e.member, month) expenseSub " +
            "LEFT JOIN (SELECT i.member AS member, TO_CHAR(i.date, 'YYYY-MM') AS month, SUM(i.amount) AS monthlyIncome FROM Income i WHERE i.member = :member AND FUNCTION('DATE_PART', 'year', i.date) = :year GROUP BY i.member, month) incomeSub " +
            "ON expenseSub.member = incomeSub.member AND expenseSub.month = incomeSub.month " +
            "GROUP BY expenseSub.month, incomeSub.monthlyIncome, expenseSub.monthlyExpense " +
            "ORDER BY MIN(expenseSub.month)")

    List<MonthlyIncomeExpenseReportDTO> getMonthlyIncomeExpenseReports(@Param("member") Member member, @Param("year") int year);

}

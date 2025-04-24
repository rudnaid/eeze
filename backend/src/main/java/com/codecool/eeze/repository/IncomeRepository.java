package com.codecool.eeze.repository;

import com.codecool.eeze.model.dto.reports.MonthlyIncomeTotal;
import com.codecool.eeze.model.entity.Income;
import com.codecool.eeze.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {

    List<Income> findIncomesByMember(Member member);

    Optional<Income> findIncomeByPublicId(UUID publicId);

    @Query("SELECT COALESCE(SUM(i.amount), 0) " +
            "FROM Income i " +
            "WHERE i.member.username = :username")
    double getTotalIncomeByMemberUsername(@Param("username") String username);

    @Query("SELECT new com.codecool.eeze.model.dto.reports.MonthlyIncomeTotal(" +
            "EXTRACT(MONTH FROM i.date) AS month, " +
            "ROUND(SUM(i.amount), 2)) AS incomeTotal " +
            "FROM Income i " +
            "WHERE i.member = :member " +
            "AND EXTRACT(YEAR FROM i.date) = :year " +
            "GROUP BY EXTRACT(MONTH FROM i.date) " +
            "ORDER BY EXTRACT(MONTH FROM i.date)")
    List<MonthlyIncomeTotal> getMonthlyTotalIncomes(Member member, int year);
}

package com.codecool.spendeeze.repository;

import com.codecool.spendeeze.model.entity.Income;
import com.codecool.spendeeze.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {
    List<Income> findIncomesByMember(Member member);
    Optional<Income> findIncomeByPublicId(UUID publicId);
}

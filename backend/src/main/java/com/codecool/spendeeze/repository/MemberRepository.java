package com.codecool.spendeeze.repository;

import com.codecool.spendeeze.model.entity.Member;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Member m WHERE m.username = :username")
    int deleteMemberByUsername(@Param("username")String username);

    Optional<Member> findMemberByUsername(String username);
}

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
    Optional<Member> getMemberByPublicId(UUID publicId);

    Optional<Member> getMemberByUsername(String username);

    @Transactional
    @Modifying
    @Query("DELETE FROM Member m WHERE m.publicId = :publicId")
    int deleteMemberByPublicId(@Param("publicId")UUID publicId);
}

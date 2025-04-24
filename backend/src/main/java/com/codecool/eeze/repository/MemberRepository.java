package com.codecool.eeze.repository;

import com.codecool.eeze.model.entity.Member;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findMemberByUsername(String username);

    @Transactional
    @Modifying
    @Query("DELETE FROM Member m WHERE m.username = :username")
    int deleteMemberByUsername(@Param("username") String username);
}

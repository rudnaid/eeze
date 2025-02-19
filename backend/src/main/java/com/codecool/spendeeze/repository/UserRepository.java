package com.codecool.spendeeze.repository;

import com.codecool.spendeeze.model.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getUserByPublicId(UUID publicId);

    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.publicId = :publicId")
    int deleteUserByPublicId(@Param("publicId")UUID publicId);
}

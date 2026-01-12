package com.campus.connect.repository;

import com.campus.connect.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Used for Authentication and Profile management.
     */
    Optional<User> findByEmail(String email);

    /**
     * Resolves the red error in ReportingService.
     * For Milestone 4, this counts all registered users in MySQL.
     */
    @Query("SELECT COUNT(u) FROM User u")
    long countActiveUsers();
}
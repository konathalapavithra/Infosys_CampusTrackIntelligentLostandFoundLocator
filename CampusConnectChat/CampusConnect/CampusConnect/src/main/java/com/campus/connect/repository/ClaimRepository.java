package com.campus.connect.repository;

import com.campus.connect.model.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long> {

    /**
     * ✅ ADD THIS LINE: Resolves red text in ReportingController.java.
     * Returns a list of claims filtered by status (e.g., "PENDING").
     */
    List<Claim> findByStatus(String status);

    /**
     * Fetches all claims made by a specific user.
     */
    List<Claim> findByClaimerEmail(String email);

    /**
     * Resolves the "red text" for countByStatus in ReportingService.
     * Used for dashboard stat cards (Items Recovered).
     */
    long countByStatus(String status);

    /**
     * Native query for admin1.html charts.
     * Groups claims by the week they were created.
     */
    @Query(value = "SELECT WEEK(created_at) as week_num, COUNT(*) as claim_count " +
            "FROM claims " +
            "GROUP BY week_num " +
            "ORDER BY week_num DESC LIMIT 4",
            nativeQuery = true)
    List<Object[]> getWeeklyClaimStats();
}
package com.campus.connect.service;

import com.campus.connect.dto.DashboardStatsDTO;
import com.campus.connect.repository.ClaimRepository;
import com.campus.connect.repository.ItemRepository;
import com.campus.connect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportingService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ClaimRepository claimRepository;

    @Autowired
    private UserRepository userRepository;

    public DashboardStatsDTO getDashboardStats() {
        DashboardStatsDTO dto = new DashboardStatsDTO();

        // 1. Basic Item Statistics
        long totalItems = itemRepository.count();
        // Uses the custom countByStatus we added to ClaimRepository
        long recoveredItems = claimRepository.countByStatus("APPROVED");

        dto.setTotalItems(totalItems);
        dto.setRecoveredItems(recoveredItems);
        dto.setNotRecoveredItems(totalItems - recoveredItems);

        // 2. Claim Statistics
        dto.setTotalClaims(claimRepository.count());

        // 3. Weekly Stats (Native Query Result Handling)
        List<Object[]> results = claimRepository.getWeeklyClaimStats();
        List<Long> weekly = new ArrayList<>();

        for (Object[] row : results) {
            // Safe conversion: row[1] is the COUNT(*) from the native query
            if (row[1] != null) {
                weekly.add(((Number) row[1]).longValue());
            } else {
                weekly.add(0L);
            }
        }
        dto.setWeeklyClaims(weekly);

        // 4. User Statistics
        dto.setTotalUsers(userRepository.count());
        dto.setActiveUsers(userRepository.countActiveUsers());

        return dto;
    }
}
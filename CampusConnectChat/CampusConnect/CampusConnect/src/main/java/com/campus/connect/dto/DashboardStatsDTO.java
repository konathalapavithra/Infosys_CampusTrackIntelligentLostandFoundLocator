package com.campus.connect.dto;

import java.util.List;

public class DashboardStatsDTO {

    private long totalItems;
    private long recoveredItems;
    private long notRecoveredItems;

    private long totalClaims;
    private List<Long> weeklyClaims;

    private long totalUsers;
    private long activeUsers;

    // getters & setters
    public long getTotalItems() { return totalItems; }
    public void setTotalItems(long totalItems) { this.totalItems = totalItems; }

    public long getRecoveredItems() { return recoveredItems; }
    public void setRecoveredItems(long recoveredItems) { this.recoveredItems = recoveredItems; }

    public long getNotRecoveredItems() { return notRecoveredItems; }
    public void setNotRecoveredItems(long notRecoveredItems) { this.notRecoveredItems = notRecoveredItems; }

    public long getTotalClaims() { return totalClaims; }
    public void setTotalClaims(long totalClaims) { this.totalClaims = totalClaims; }

    public List<Long> getWeeklyClaims() { return weeklyClaims; }
    public void setWeeklyClaims(List<Long> weeklyClaims) { this.weeklyClaims = weeklyClaims; }

    public long getTotalUsers() { return totalUsers; }
    public void setTotalUsers(long totalUsers) { this.totalUsers = totalUsers; }

    public long getActiveUsers() { return activeUsers; }
    public void setActiveUsers(long activeUsers) { this.activeUsers = activeUsers; }
}

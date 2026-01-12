package com.campus.connect.controller;

import com.campus.connect.dto.DashboardStatsDTO;
import com.campus.connect.service.ReportingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin1")
@CrossOrigin(origins = "*") // Allows the frontend to fetch data from port 8080
public class Admin1Controller {

    @Autowired
    private ReportingService reportingService;

    /**
     * Admin1 Dashboard API
     * Maps to the fetch request in admin1.html.
     */
    @GetMapping("/dashboard")
    public DashboardStatsDTO getDashboardStats() {
        // Returns the DTO which is automatically converted to JSON
        return reportingService.getDashboardStats();
    }
}
package com.campus.connect.controller;

import com.campus.connect.model.Claim;
import com.campus.connect.repository.ClaimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*")
public class ReportingController {

    @Autowired
    private ClaimRepository claimRepository;

    // Example: get all claims
    @GetMapping("/claims")
    public List<Claim> getAllClaims() {
        return claimRepository.findAll();
    }

    // Example: get claims by status
    @GetMapping("/claims/status/{status}")
    public List<Claim> getClaimsByStatus(@PathVariable String status) {
        return claimRepository.findByStatus(status);
    }

    // ❌ NO dashboard endpoint here
}

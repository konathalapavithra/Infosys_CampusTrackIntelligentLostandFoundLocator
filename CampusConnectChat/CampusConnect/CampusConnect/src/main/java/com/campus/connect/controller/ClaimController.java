package com.campus.connect.controller;

import com.campus.connect.model.Claim;
import com.campus.connect.repository.ClaimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/claims")
@CrossOrigin(origins = "*") // Allows communication with your frontend dashboard
public class ClaimController {

    @Autowired
    private ClaimRepository claimRepository;

    /**
     * Submits a new claim from a student.
     */
    @PostMapping
    public Claim submitClaim(@RequestBody Claim claim) {
        System.out.println("Received claim for item ID: " + claim.getItemId());
        // 'createdAt' will be handled automatically by MySQL
        return claimRepository.save(claim);
    }

    /**
     * Retrieves claims for the 'My Claims' section for students.
     */
    @GetMapping("/user/{email}")
    public List<Claim> getUserClaims(@PathVariable String email) {
        return claimRepository.findByClaimerEmail(email);
    }

    /**
     * Retrieves all claims for the Admin Dashboard.
     */
    @GetMapping
    public List<Claim> getAllClaims() {
        return claimRepository.findAll();
    }

    /**
     * Updates status (APPROVED/REJECTED) from the Admin Dashboard.
     * Linked to the 'Approve' and 'Reject' buttons in your HTML.
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<Claim> updateClaimStatus(@PathVariable Long id, @RequestParam String status) {
        return claimRepository.findById(id)
                .map(claim -> {
                    claim.setStatus(status);
                    Claim updated = claimRepository.save(claim);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
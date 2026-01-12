package com.campus.connect.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

@Entity
@Table(name = "claims")
public class Claim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_id", nullable = false)
    private Long itemId;

    @Column(name = "proof_of_ownership", columnDefinition = "TEXT", nullable = false)
    @JsonProperty("proofOfOwnership")
    private String proofOfOwnership;

    @Column(name = "claimer_email", nullable = false)
    @JsonProperty("claimerEmail")
    private String claimerEmail;

    private String status = "PENDING";

    /**
     * UPDATED FOR ANALYTICS:
     * Renamed to 'created_at' to match the Native Query in ClaimRepository.
     * Added 'columnDefinition' to ensure MySQL handles the default timestamp.
     */
    @Column(name = "created_at", insertable = false, updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    // --- Getters and Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getItemId() { return itemId; }
    public void setItemId(Long itemId) { this.itemId = itemId; }

    public String getProofOfOwnership() { return proofOfOwnership; }
    public void setProofOfOwnership(String proofOfOwnership) { this.proofOfOwnership = proofOfOwnership; }

    public String getClaimerEmail() { return claimerEmail; }
    public void setClaimerEmail(String claimerEmail) { this.claimerEmail = claimerEmail; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
package com.campus.connect.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 1000)
    private String description;

    private String category;

    // Renamed from 'location' to 'specificLocation' for clarity
    @Column(name = "specific_location")
    private String specificLocation; 

    private String date; 

    @Column(name = "image_url") 
    private String imageUrl; 

    @Enumerated(EnumType.STRING)
    @JsonProperty("itemType")
    private ItemType itemType; 

    @Enumerated(EnumType.STRING)
    @Column(name = "campus_zone")
    private CampusZone campusZone;

    @Column(name = "reporter_email")
    @JsonProperty("reporterEmail")
    private String reporterEmail;

    @Column(name = "status")
    private String status = "AVAILABLE";

    private LocalDateTime createdAt = LocalDateTime.now();

    // Enums
    public enum ItemType { LOST, FOUND }
    
    public enum CampusZone {
        NORTH_GATE, SOUTH_GATE, MAIN_LIBRARY, CENTRAL_CAFETERIA, 
        ENGINEERING_BLOCK, SCIENCE_LAB, SPORTS_COMPLEX, OTHER
    }

    // --- Getters and Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getSpecificLocation() { return specificLocation; }
    public void setSpecificLocation(String specificLocation) { this.specificLocation = specificLocation; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public ItemType getItemType() { return itemType; }
    public void setItemType(ItemType itemType) { this.itemType = itemType; }

    public CampusZone getCampusZone() { return campusZone; }
    public void setCampusZone(CampusZone campusZone) { this.campusZone = campusZone; }

    public String getReporterEmail() { return reporterEmail; }
    public void setReporterEmail(String reporterEmail) { this.reporterEmail = reporterEmail; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
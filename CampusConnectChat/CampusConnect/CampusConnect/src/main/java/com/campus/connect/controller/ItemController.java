package com.campus.connect.controller;

import com.campus.connect.model.Item;
import com.campus.connect.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@RequestMapping("/api/items")
@CrossOrigin(origins = "*") 
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping
    public List<Item> getAllItems() {
        return itemRepository.findAll(); 
    }

    // Standard JSON report (without image)
    @PostMapping("/report")
    public ResponseEntity<?> reportItem(@RequestBody Item item) {
        try {
            Item savedItem = itemRepository.save(item);
            return ResponseEntity.ok(savedItem);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // UPDATED FOR MILESTONE 3: Structured Location & Matching
    @PostMapping("/report-with-image")
    public ResponseEntity<?> reportItemWithImage(
            @RequestParam("title") String title,
            @RequestParam("category") String category,
            @RequestParam("description") String description,
            @RequestParam("campusZone") String campusZone, // NEW
            @RequestParam("specificLocation") String specificLocation, // NEW
            @RequestParam("date") String date,
            @RequestParam("itemType") String itemType,
            @RequestParam("reporterEmail") String reporterEmail,
            @RequestParam("status") String status,
            @RequestParam(value = "image", required = false) MultipartFile file) {

        try {
            Item item = new Item();
            item.setTitle(title);
            item.setCategory(category);
            item.setDescription(description);
            
            // Map the new location fields
            item.setCampusZone(Item.CampusZone.valueOf(campusZone.toUpperCase()));
            item.setSpecificLocation(specificLocation);
            
            item.setDate(date);
            item.setItemType(Item.ItemType.valueOf(itemType.toUpperCase()));
            item.setReporterEmail(reporterEmail);
            item.setStatus(status);

            if (file != null && !file.isEmpty()) {
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path uploadPath = Paths.get("src/main/resources/static/uploads/").toAbsolutePath().normalize();
                
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                
                item.setImageUrl("/uploads/" + fileName);
            }

            itemRepository.save(item);
            return ResponseEntity.ok("Item reported successfully!");
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid Enum Value: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Upload failed: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        itemRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/returned")
    public ResponseEntity<Void> markAsReturned(@PathVariable Long id) {
        Item item = itemRepository.findById(id).orElseThrow();
        item.setStatus("RETURNED");
        itemRepository.save(item);
        return ResponseEntity.ok().build();
    }
    
    @Autowired
    private com.campus.connect.service.ItemMatchingService matchingService;

    @GetMapping("/{id}/matches")
    public ResponseEntity<List<Item>> getMatches(@PathVariable Long id) {
        Item targetItem = itemRepository.findById(id).orElseThrow();
        List<Item> allItems = itemRepository.findAll();
        List<Item> matches = matchingService.findPotentialMatches(targetItem, allItems);
        return ResponseEntity.ok(matches);
    }
    
    @GetMapping("/my-items")
    public ResponseEntity<List<Item>> getMyItems(@RequestParam String email) {
        List<Item> items = itemRepository.findByReporterEmail(email);
        return ResponseEntity.ok(items);
    }
    
}
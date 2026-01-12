package com.campus.connect.service;

import com.campus.connect.model.Item;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemMatchingService {

    private final LevenshteinDistance levenshtein = new LevenshteinDistance();

    public List<Item> findPotentialMatches(Item targetItem, List<Item> allItems) {
        return allItems.stream()
                .filter(item -> !item.getId().equals(targetItem.getId())) 
                .filter(item -> !item.getItemType().equals(targetItem.getItemType())) 
                .filter(item -> calculateMatchScore(targetItem, item) >= 50.0) 
                .sorted((a, b) -> Double.compare(calculateMatchScore(targetItem, b), calculateMatchScore(targetItem, a)))
                // Changed from .toList() to .collect(Collectors.toList()) for better compatibility
                .collect(Collectors.toList());
    }

    public double calculateMatchScore(Item item1, Item item2) {
        double score = 0.0;

        // 1. Category Match (40 pts)
        if (item1.getCategory() != null && item1.getCategory().equalsIgnoreCase(item2.getCategory())) {
            score += 40.0;
        }

        // 2. Campus Zone Match (30 pts)
        if (item1.getCampusZone() != null && item1.getCampusZone().equals(item2.getCampusZone())) {
            score += 30.0;
        }

        // 3. Fuzzy Title Match (30 pts)
        String s1 = item1.getTitle().toLowerCase();
        String s2 = item2.getTitle().toLowerCase();
        int distance = levenshtein.apply(s1, s2);
        int maxLen = Math.max(s1.length(), s2.length());
        
        if (maxLen > 0) {
            score += (1.0 - ((double) distance / maxLen)) * 30.0;
        }

        return score;
    }
}
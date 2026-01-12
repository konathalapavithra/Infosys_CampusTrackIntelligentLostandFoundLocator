package com.campus.connect.repository;

import com.campus.connect.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByStatus(String status);

    // This allows the dashboard to fetch items specific to one user
    List<Item> findByReporterEmail(String reporterEmail);
    long countByStatus(String status);
}
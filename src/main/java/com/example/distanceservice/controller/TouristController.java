package com.example.distanceservice.controller;

import com.example.distanceservice.entity.Tourist;
import com.example.distanceservice.service.TouristService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tourists")
public class TouristController {
    private final TouristService touristService;

    public TouristController(TouristService touristService) {
        this.touristService = touristService;
    }

    @GetMapping
    public List<Tourist> getAllTourists() {
        return touristService.getAllTourists();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tourist> getTouristById(@PathVariable Long id) {
        return touristService.getTouristById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Tourist createTourist(@RequestBody Tourist tourist) {
        return touristService.saveTourist(tourist);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tourist> updateTourist(@PathVariable Long id, @RequestBody Tourist tourist) {
        try {
            return ResponseEntity.ok(touristService.updateTourist(id, tourist));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTourist(@PathVariable Long id) {
        touristService.deleteTourist(id);
        return ResponseEntity.ok().build();
    }
}

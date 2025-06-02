package com.example.distanceservice.service;

import com.example.distanceservice.cache.SimpleCache;
import com.example.distanceservice.entity.Tourist;
import com.example.distanceservice.repository.TouristRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TouristService {
    private final TouristRepository touristRepository;
    private final SimpleCache simpleCache;

    public TouristService(TouristRepository touristRepository, SimpleCache simpleCache) {
        this.touristRepository = touristRepository;
        this.simpleCache = simpleCache;
    }

    public List<Tourist> getAllTourists() {
        return touristRepository.findAll();
    }

    public Optional<Tourist> getTouristById(Long id) {
        return touristRepository.findById(id);
    }

    public Tourist saveTourist(Tourist tourist) {
        Tourist savedTourist = touristRepository.save(tourist);
        simpleCache.put("tourists_all", touristRepository.findAll());
        return savedTourist;
    }

    public void deleteTourist(Long id) {
        touristRepository.deleteById(id);
        simpleCache.put("tourists_all", touristRepository.findAll());
    }

    public Tourist updateTourist(Long id, Tourist updatedTourist) {
        Tourist updated = touristRepository.findById(id).map(tourist -> {
            tourist.setName(updatedTourist.getName());
            tourist.setVisitedCities(updatedTourist.getVisitedCities());
            return touristRepository.save(tourist);
        }).orElseThrow(() -> new RuntimeException("Tourist not found with id: " + id));
        simpleCache.put("tourists_all", touristRepository.findAll());
        return updated;
    }
}

package com.example.distanceservice.service;

import com.example.distanceservice.entity.Tourist;
import com.example.distanceservice.repository.TouristRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TouristService {
    private final TouristRepository touristRepository;

    public TouristService(TouristRepository touristRepository) {
        this.touristRepository = touristRepository;
    }

    public List<Tourist> getAllTourists() {
        return touristRepository.findAll();
    }

    public Optional<Tourist> getTouristById(Long id) {
        return touristRepository.findById(id);
    }

    public Tourist saveTourist(Tourist tourist) {
        return touristRepository.save(tourist);
    }

    public void deleteTourist(Long id) {
        touristRepository.deleteById(id);
    }

    public Tourist updateTourist(Long id, Tourist updatedTourist) {
        return touristRepository.findById(id).map(tourist -> {
            tourist.setName(updatedTourist.getName());
            tourist.setVisitedCities(updatedTourist.getVisitedCities());
            return touristRepository.save(tourist);
        }).orElseThrow(() -> new RuntimeException("Tourist not found with id: " + id));
    }
}

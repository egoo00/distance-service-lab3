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
        List<Tourist> cachedTourists = (List<Tourist>) simpleCache.get("tourists_all");
        if (cachedTourists != null) {
            return cachedTourists;
        }
        List<Tourist> tourists = touristRepository.findAll();
        simpleCache.put("tourists_all", tourists);
        return tourists;
    }

    public Optional<Tourist> getTouristById(Long id) {
        String cacheKey = "tourist_" + id;
        Optional<Tourist> cachedTourist = Optional.ofNullable((Tourist) simpleCache.get(cacheKey));
        if (cachedTourist.isPresent()) {
            return cachedTourist;
        }
        Optional<Tourist> tourist = touristRepository.findById(id);
        tourist.ifPresent(t -> simpleCache.put(cacheKey, t));
        return tourist;
    }

    public Tourist saveTourist(Tourist tourist) {
        Tourist savedTourist = touristRepository.save(tourist);
        List<Tourist> updatedTourists = touristRepository.findAll();
        simpleCache.put("tourists_all", updatedTourists);
        simpleCache.put("tourist_" + savedTourist.getId(), savedTourist);
        return savedTourist;
    }

    public void deleteTourist(Long id) {
        touristRepository.deleteById(id);
        List<Tourist> updatedTourists = touristRepository.findAll();
        simpleCache.put("tourists_all", updatedTourists);
        simpleCache.put("tourist_" + id, null);
    }

    public Tourist updateTourist(Long id, Tourist updatedTourist) {
        Tourist updated = touristRepository.findById(id).map(tourist -> {
            tourist.setName(updatedTourist.getName());
            tourist.setVisitedCities(updatedTourist.getVisitedCities());
            return touristRepository.save(tourist);
        }).orElseThrow(() -> new RuntimeException("Tourist not found with id: " + id));
        List<Tourist> updatedTourists = touristRepository.findAll();
        simpleCache.put("tourists_all", updatedTourists);
        simpleCache.put("tourist_" + id, updated);
        return updated;
    }
}

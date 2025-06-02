package com.example.distanceservice.service;

import com.example.distanceservice.cache.SimpleCache;
import com.example.distanceservice.entity.City;
import com.example.distanceservice.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {
    private final CityRepository cityRepository;
    private final SimpleCache simpleCache;

    public CityService(CityRepository cityRepository, SimpleCache simpleCache) {
        this.cityRepository = cityRepository;
        this.simpleCache = simpleCache;
    }

    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    public Optional<City> getCityById(Long id) {
        return cityRepository.findById(id);
    }

    public City saveCity(City city) {
        City savedCity = cityRepository.save(city);
        simpleCache.put("cities_all", cityRepository.findAll());
        return savedCity;
    }

    public void deleteCity(Long id) {
        cityRepository.deleteById(id);
        simpleCache.put("cities_all", cityRepository.findAll());
    }

    public City updateCity(Long id, City updatedCity) {
        City updated = cityRepository.findById(id).map(city -> {
            city.setName(updatedCity.getName());
            city.setLatitude(updatedCity.getLatitude());
            city.setLongitude(updatedCity.getLongitude());
            return cityRepository.save(city);
        }).orElseThrow(() -> new RuntimeException("City not found with id: " + id));
        simpleCache.put("cities_all", cityRepository.findAll());
        return updated;
    }
}

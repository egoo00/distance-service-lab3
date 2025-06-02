package com.example.distanceservice.service;

import com.example.distanceservice.entity.City;
import com.example.distanceservice.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {
    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    public Optional<City> getCityById(Long id) {
        return cityRepository.findById(id);
    }

    public City saveCity(City city) {
        return cityRepository.save(city);
    }

    public void deleteCity(Long id) {
        cityRepository.deleteById(id);
    }

    public City updateCity(Long id, City updatedCity) {
        return cityRepository.findById(id).map(city -> {
            city.setName(updatedCity.getName());
            city.setLatitude(updatedCity.getLatitude());
            city.setLongitude(updatedCity.getLongitude());
            return cityRepository.save(city);
        }).orElseThrow(() -> new RuntimeException("City not found with id: " + id));
    }
}

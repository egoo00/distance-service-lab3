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
        List<City> cachedCities = (List<City>) simpleCache.get("cities_all");
        if (cachedCities != null) {
            return cachedCities;
        }
        List<City> cities = cityRepository.findAll();
        simpleCache.put("cities_all", cities);
        return cities;
    }

    public Optional<City> getCityById(Long id) {
        String cacheKey = "city_" + id;
        Optional<City> cachedCity = Optional.ofNullable((City) simpleCache.get(cacheKey));
        if (cachedCity.isPresent()) {
            return cachedCity;
        }
        Optional<City> city = cityRepository.findById(id);
        city.ifPresent(c -> simpleCache.put(cacheKey, c));
        return city;
    }

    public City saveCity(City city) {
        City savedCity = cityRepository.save(city);
        List<City> updatedCities = cityRepository.findAll();
        simpleCache.put("cities_all", updatedCities);
        simpleCache.put("city_" + savedCity.getId(), savedCity);
        return savedCity;
    }

    public void deleteCity(Long id) {
        cityRepository.deleteById(id);
        List<City> updatedCities = cityRepository.findAll();
        simpleCache.put("cities_all", updatedCities);
        simpleCache.put("city_" + id, null);
    }

    public City updateCity(Long id, City updatedCity) {
        City updated = cityRepository.findById(id).map(city -> {
            city.setName(updatedCity.getName());
            city.setLatitude(updatedCity.getLatitude());
            city.setLongitude(updatedCity.getLongitude());
            return cityRepository.save(city);
        }).orElseThrow(() -> new RuntimeException("City not found with id: " + id));
        List<City> updatedCities = cityRepository.findAll();
        simpleCache.put("cities_all", updatedCities);
        simpleCache.put("city_" + id, updated);
        return updated;
    }
}

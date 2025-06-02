package com.example.distanceservice.service;

import com.example.distanceservice.cache.SimpleCache;
import com.example.distanceservice.entity.City;
import com.example.distanceservice.exception.CityNotFoundException;
import com.example.distanceservice.repository.CityRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeocodingService {
    private final RestTemplate restTemplate;
    private final CityRepository cityRepository;
    private final SimpleCache simpleCache;

    @Value("${geocoding.api.url}")
    private String apiUrl;

    @Value("${geocoding.api.key}")
    private String apiKey;

    public GeocodingService(RestTemplateBuilder restTemplateBuilder, CityRepository cityRepository, SimpleCache simpleCache) {
        this.restTemplate = restTemplateBuilder.build();
        this.cityRepository = cityRepository;
        this.simpleCache = simpleCache;
    }

    public City getCity(String cityName) {
        String cacheKey = "geocoded_city_" + cityName;
        City cachedCity = (City) simpleCache.get(cacheKey);
        if (cachedCity != null) {
            return cachedCity;
        }

        City city = cityRepository.findByName(cityName).orElse(null);
        if (city != null) {
            simpleCache.put(cacheKey, city);
            return city;
        }

        String url = String.format("%s?q=%s&key=%s", apiUrl, cityName, apiKey);
        ResponseEntity<com.example.distanceservice.dto.GeocodingResponse> response = restTemplate.getForEntity(url, com.example.distanceservice.dto.GeocodingResponse.class);

        if (response.getBody() == null || response.getBody().getResults() == null || response.getBody().getResults().isEmpty()) {
            return null;
        }

        com.example.distanceservice.dto.Geometry geometry = response.getBody().getResults().get(0).getGeometry();
        City newCity = new City(cityName, geometry.getLat(), geometry.getLng());
        City savedCity = cityRepository.save(newCity);
        simpleCache.put(cacheKey, savedCity);
        return savedCity;
    }
} gfgfgfh

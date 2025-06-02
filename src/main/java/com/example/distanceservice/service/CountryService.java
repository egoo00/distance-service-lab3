package com.example.distanceservice.service;

import com.example.distanceservice.cache.SimpleCache;
import com.example.distanceservice.entity.Country;
import com.example.distanceservice.repository.CountryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryService {
    private final CountryRepository countryRepository;
    private final SimpleCache simpleCache;

    public CountryService(CountryRepository countryRepository, SimpleCache simpleCache) {
        this.countryRepository = countryRepository;
        this.simpleCache = simpleCache;
    }

    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    public Optional<Country> getCountryById(Long id) {
        return countryRepository.findById(id);
    }

    public Country saveCountry(Country country) {
        Country savedCountry = countryRepository.save(country);
        simpleCache.put("countries_all", countryRepository.findAll());
        return savedCountry;
    }

    public void deleteCountry(Long id) {
        countryRepository.deleteById(id);
        simpleCache.put("countries_all", countryRepository.findAll());
    }

    public Country updateCountry(Long id, Country updatedCountry) {
        Country updated = countryRepository.findById(id).map(country -> {
            country.setName(updatedCountry.getName());
            return countryRepository.save(country);
        }).orElseThrow(() -> new RuntimeException("Country not found with id: " + id));
        simpleCache.put("countries_all", countryRepository.findAll());
        return updated;
    }
}

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
        List<Country> cachedCountries = (List<Country>) simpleCache.get("countries_all");
        if (cachedCountries != null) {
            return cachedCountries;
        }
        List<Country> countries = countryRepository.findAll();
        simpleCache.put("countries_all", countries);
        return countries;
    }

    public Optional<Country> getCountryById(Long id) {
        String cacheKey = "country_" + id;
        Optional<Country> cachedCountry = Optional.ofNullable((Country) simpleCache.get(cacheKey));
        if (cachedCountry.isPresent()) {
            return cachedCountry;
        }
        Optional<Country> country = countryRepository.findById(id);
        country.ifPresent(c -> simpleCache.put(cacheKey, c));
        return country;
    }

    public Country saveCountry(Country country) {
        Country savedCountry = countryRepository.save(country);
        List<Country> updatedCountries = countryRepository.findAll();
        simpleCache.put("countries_all", updatedCountries);
        simpleCache.put("country_" + savedCountry.getId(), savedCountry);
        return savedCountry;
    }

    public void deleteCountry(Long id) {
        countryRepository.deleteById(id);
        List<Country> updatedCountries = countryRepository.findAll();
        simpleCache.put("countries_all", updatedCountries);
        simpleCache.put("country_" + id, null);
    }

    public Country updateCountry(Long id, Country updatedCountry) {
        Country updated = countryRepository.findById(id).map(country -> {
            country.setName(updatedCountry.getName());
            return countryRepository.save(country);
        }).orElseThrow(() -> new RuntimeException("Country not found with id: " + id));
        List<Country> updatedCountries = countryRepository.findAll();
        simpleCache.put("countries_all", updatedCountries);
        simpleCache.put("country_" + id, updated);
        return updated;
    }
}

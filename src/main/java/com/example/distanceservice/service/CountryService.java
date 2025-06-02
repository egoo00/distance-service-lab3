package com.example.distanceservice.service;

import com.example.distanceservice.entity.Country;
import com.example.distanceservice.repository.CountryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryService {
    private final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    public Optional<Country> getCountryById(Long id) {
        return countryRepository.findById(id);
    }

    public Country saveCountry(Country country) {
        return countryRepository.save(country);
    }

    public void deleteCountry(Long id) {
        countryRepository.deleteById(id);
    }

    public Country updateCountry(Long id, Country updatedCountry) {
        return countryRepository.findById(id).map(country -> {
            country.setName(updatedCountry.getName());
            return countryRepository.save(country);
        }).orElseThrow(() -> new RuntimeException("Country not found with id: " + id));
    }
}

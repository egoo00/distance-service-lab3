package com.example.distanceservice;

import com.example.distanceservice.entity.City;
import com.example.distanceservice.entity.Country;
import com.example.distanceservice.entity.Tourist;
import com.example.distanceservice.repository.CityRepository;
import com.example.distanceservice.repository.CountryRepository;
import com.example.distanceservice.repository.TouristRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final TouristRepository touristRepository;

    public DataLoader(CityRepository cityRepository, CountryRepository countryRepository, TouristRepository touristRepository) {
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
        this.touristRepository = touristRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Country russia = new Country();
        russia.setName("Russia");
        countryRepository.save(russia);

        Country uk = new Country();
        uk.setName("United Kingdom");
        countryRepository.save(uk);

        City moscow = new City();
        moscow.setName("Moscow");
        moscow.setLatitude(55.7558);
        moscow.setLongitude(37.6173);
        moscow.setCountry(russia);
        cityRepository.save(moscow);

        City london = new City();
        london.setName("London");
        london.setLatitude(51.5074);
        london.setLongitude(-0.1278);
        london.setCountry(uk);
        cityRepository.save(london);

        Tourist ivan = new Tourist();
        ivan.setName("Ivan");
        ivan.setVisitedCities(Arrays.asList(moscow));
        touristRepository.save(ivan);

        Tourist john = new Tourist();
        john.setName("John");
        john.setVisitedCities(Arrays.asList(london));
        touristRepository.save(john);

        Tourist alice = new Tourist();
        alice.setName("Alice");
        alice.setVisitedCities(Arrays.asList(moscow, london));
        touristRepository.save(alice);
    }
}

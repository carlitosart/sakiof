package com.sakila.sakila.bl;

import com.sakila.sakila.dao.CountryRepository;
import com.sakila.sakila.dao.FilmRepository;
import com.sakila.sakila.dto.Country;
import com.sakila.sakila.dto.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryBl {

    CountryRepository countryRepository;
    @Autowired
    public CountryBl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }
    public List<Country> getCountries(){


        return countryRepository.getAvailableCountries();
    }
}

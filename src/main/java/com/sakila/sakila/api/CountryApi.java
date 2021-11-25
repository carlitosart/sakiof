package com.sakila.sakila.api;

import com.sakila.sakila.bl.CountryBl;
import com.sakila.sakila.dto.Country;
import com.sakila.sakila.dto.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/country")
public class CountryApi {
    CountryBl countryBl;
    @Autowired
    CountryApi(CountryBl countryBl){
        this.countryBl=countryBl;
    }
    @GetMapping( produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Country> getFilms(){
        return countryBl.getCountries();
    }
}

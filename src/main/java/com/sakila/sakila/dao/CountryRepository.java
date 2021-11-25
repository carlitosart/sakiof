package com.sakila.sakila.dao;

import com.sakila.sakila.dto.Country;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface CountryRepository extends JpaRepository<Country,Integer> {
    @Query(value="select country.* from country INNER JOIN city c on country.country_id = c.country_id INNER JOIN address a on c.city_id = a.city_id INNER JOIN store s on a.address_id = s.address_id",nativeQuery = true)
    List<Country> getAvailableCountries();
}

package com.sakila.sakila.api;

import com.sakila.sakila.bl.FilmBl;
import com.sakila.sakila.dto.Film;
import com.sakila.sakila.dto.RentalRequest;
import com.sakila.sakila.dto.RentalResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/film")
public class FilmApi {
    private FilmBl filmBl;

    @Autowired
    public FilmApi(FilmBl filmBl) {
        this.filmBl = filmBl;
    }

    @GetMapping( produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Film> getFilms(@RequestParam(name = "country_id") Integer countryId,@RequestParam(name = "query",required = false) String query,@RequestParam(name = "actor",required = false) String actor,@RequestParam(name = "page") Integer page) {

        return filmBl.getFilms(countryId,query,actor,page);
    }
    @GetMapping(value = "/most-rented" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Film> getMostRentedFilms(@RequestParam(name = "country_id") Integer countryId,@RequestParam(name = "date",required = false) Date date,@RequestParam(name = "page") Integer page) {

        return filmBl.getMostRentedFilms(countryId,date,page);
    }
    @PostMapping(value = "/rent" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public RentalResponse rentFilm(@RequestParam(name = "country_id") Integer countryId, @RequestBody RentalRequest rentalRequest){
        return filmBl.rentFilm(rentalRequest,countryId);
    }
}

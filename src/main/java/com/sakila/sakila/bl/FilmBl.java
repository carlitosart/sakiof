package com.sakila.sakila.bl;

import com.sakila.sakila.dao.FilmRepository;
import com.sakila.sakila.dao.RentalRepository;
import com.sakila.sakila.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class FilmBl {
    FilmRepository filmRepository;
    RentalRepository rentalRepository;
    @Autowired
    public FilmBl(FilmRepository filmRepository,RentalRepository rentalRepository) {
        this.filmRepository = filmRepository;
        this.rentalRepository=rentalRepository;
    }
    public List<Film> getFilms(Integer countryId,String query,String actor,Integer page){

        if(query!=null)query="*"+query+"*";
        if(actor!=null)actor="*"+actor+"*";

        return filmRepository.findFilms(countryId,query,actor,page*10,10);
    }
    public List<Film> getMostRentedFilms(Integer countryId,Date date,Integer page){

        return filmRepository.getAllByByDate(countryId,date,page*10,10);
    }
    public RentalResponse rentFilm(RentalRequest rentalRequest,Integer countryId){
        Authentication a= SecurityContextHolder.getContext().getAuthentication();
        System.out.println(a.getPrincipal());
        UserInformation userInformation=(UserInformation) a.getPrincipal();
        RentalResponse rentalResponse=new RentalResponse();
        rentalResponse.setRented(new ArrayList<>());
        rentalResponse.setNotRented(new ArrayList<>());
        for(Integer filmId: rentalRequest.getFilmIds()){
            try{
                Integer inventoryId=filmRepository.getInventoryId(filmId,countryId);
                if(inventoryId>0){
                    RentalModel rentalModel=new RentalModel();
                    rentalModel.setCustomerId(userInformation.getIdCustomer());
                    rentalModel.setInventoryId(inventoryId);
                    rentalModel.setRentalDate(new Date(new java.util.Date().getTime()));
                    rentalModel.setReturnDate(null);
                    rentalModel.setStaffId(1);
                    rentalRepository.save(rentalModel);
                    rentalResponse.getRented().add(filmId);
                }
                else{
                    rentalResponse.getNotRented().add(filmId);
                }
            }
            catch (Exception e){
                rentalResponse.getNotRented().add(filmId);
            }
        }
        return rentalResponse;
    }
}

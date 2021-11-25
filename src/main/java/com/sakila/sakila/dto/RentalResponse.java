package com.sakila.sakila.dto;

import java.util.List;

public class RentalResponse {
    private List<Integer> rented;
    private List<Integer> notRented;

    public List<Integer> getRented() {
        return rented;
    }

    public void setRented(List<Integer> rented) {
        this.rented = rented;
    }

    public List<Integer> getNotRented() {
        return notRented;
    }

    public void setNotRented(List<Integer> notRented) {
        this.notRented = notRented;
    }
}

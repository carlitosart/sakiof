package com.sakila.sakila.dto;

import java.util.List;

public class RentalRequest {
    private List<Integer> filmIds;

    public List<Integer> getFilmIds() {
        return filmIds;
    }

    public void setFilmIds(List<Integer> filmIds) {
        this.filmIds = filmIds;
    }
}

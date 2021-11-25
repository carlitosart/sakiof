package com.sakila.sakila.dao;

import com.sakila.sakila.dto.RentalModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository  extends JpaRepository<RentalModel,Integer> {
}

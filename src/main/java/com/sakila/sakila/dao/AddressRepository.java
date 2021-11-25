package com.sakila.sakila.dao;

import com.sakila.sakila.dto.AddressModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AddressRepository extends JpaRepository<AddressModel,Integer> {

}

package com.sakila.sakila.dao;

import com.sakila.sakila.dto.CustomerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository  extends JpaRepository<CustomerModel,Integer> {
    @Query(value = "select c.* from customer c where email=?1", nativeQuery = true)
    CustomerModel findByEmail(String email);
}

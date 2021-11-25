package com.sakila.sakila.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UserInformation  extends User {
    private Integer idCustomer;
    private String email;
    public UserInformation(String username,Integer idCustomer,String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.email=username;
        this.idCustomer=idCustomer;
    }
    public Integer getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(Integer idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

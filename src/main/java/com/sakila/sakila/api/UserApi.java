package com.sakila.sakila.api;

import com.sakila.sakila.bl.UserService;
import com.sakila.sakila.dto.Film;
import com.sakila.sakila.dto.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserApi {
    UserService userService;
    @Autowired
    UserApi(UserService userService){
        this.userService=userService;
    }
    @PostMapping( produces = MediaType.APPLICATION_JSON_VALUE)
    public void signUp(@RequestBody UserRequest userRequest){
        userService.signUp(userRequest);
    }
}

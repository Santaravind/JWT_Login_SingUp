package com.JWT.Authentication.controller;

import com.JWT.Authentication.LoginResponce;
import com.JWT.Authentication.dto.LoginUserDto;
import com.JWT.Authentication.entity.User;
import com.JWT.Authentication.service.JWTService;
import com.JWT.Authentication.service.UserService;
import jdk.jfr.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;


@Controller
@RestController
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private JWTService jwtService;

    @PostMapping("/auth/signup")
    public ResponseEntity<User> postMethodName(@RequestBody User user){
       // User user1=service.signup(user);
        return ResponseEntity.ok(service.signup(user));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponce> loginUser(@RequestBody LoginUserDto loginUserDto){
        User user=service.loginUser(loginUserDto);
    String jwtToken=jwtService.generateToken(new HashMap<>(),user);
    LoginResponce loginResponce =new LoginResponce();
    loginResponce.setToken(jwtToken);
    loginResponce.setTokenExpireTime(jwtService.getExpriretionTime());

 return  ResponseEntity.ok(loginResponce);
    }

    @GetMapping("/auth/getUser")
    public ResponseEntity<List<User>> getAllUser(){
       // List<User> users =service.getAllUser();
        return ResponseEntity.ok(service.getAllUser());
    }


}

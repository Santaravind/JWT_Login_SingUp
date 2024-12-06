package com.JWT.Authentication.service;

import com.JWT.Authentication.dto.LoginUserDto;
import com.JWT.Authentication.entity.User;
import com.JWT.Authentication.repositery.UserRepositery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepositery userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public User signup(User userData) {

        userData.setPassword(passwordEncoder.encode(userData.getPassword()));
        return userRepo.save(userData);
    }

    public User loginUser(LoginUserDto loginUserDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUserDto.getEmail(), loginUserDto.getPassword()
                )
        );

        return userRepo.findByEmail(loginUserDto.getEmail())
                .orElseThrow();
    }

    public List<User> getAllUser() {

        return userRepo.findAll();
    }






}

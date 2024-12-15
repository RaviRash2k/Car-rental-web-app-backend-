package com.example.Car.rental.web.dto;

import com.example.Car.rental.web.enums.UserRole;
import lombok.Data;

@Data
public class AuthenticationResponse {

    private String jwt;
    private UserRole userRole;
    private Long userID;
}

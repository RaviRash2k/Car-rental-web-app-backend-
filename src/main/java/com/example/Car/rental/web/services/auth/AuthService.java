package com.example.Car.rental.web.services.auth;

import com.example.Car.rental.web.dto.SignupRequest;
import com.example.Car.rental.web.dto.UserDTO;

public interface AuthService {

    UserDTO createUser(SignupRequest signupRequest);

    Boolean hasUserEmail(String email);
}

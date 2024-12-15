package com.example.Car.rental.web.dto;

import com.example.Car.rental.web.enums.UserRole;
import lombok.Data;

@Data
public class UserDTO {

    private int id;
    private String name;
    private String email;
    private UserRole userRole;
}

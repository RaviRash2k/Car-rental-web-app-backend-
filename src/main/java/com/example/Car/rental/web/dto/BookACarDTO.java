package com.example.Car.rental.web.dto;

import com.example.Car.rental.web.enums.BookCarStatus;
import lombok.Data;

import java.util.Date;
@Data
public class BookACarDTO {
    private Long id;
    private Date fromDate;
    private Date toDate;
    private Long days;
    private Long price;
    private BookCarStatus bookCarStatus;
    private int userId;
    private Long carId;
    private String userName;
    private String email;
}

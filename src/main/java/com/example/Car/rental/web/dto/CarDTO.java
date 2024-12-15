package com.example.Car.rental.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
public class CarDTO {
    private long id;
    private String brand;
    private String name;
    private String type;
    private String color;
    private String transmission;
    private String description;
    private Long price;

    @JsonFormat(pattern = "yyyy.MM.dd")
    private Date year;
//    private MultipartFile img;
//    private byte[] returnedImage;
}

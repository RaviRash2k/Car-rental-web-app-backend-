package com.example.Car.rental.web.entity;

import com.example.Car.rental.web.dto.CarDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String brand;
    private String name;
    private String color;
    private String type;
    private String transmission;
    private String description;
    private Long price;
    private Date year;
    @Column(columnDefinition = "longblob")
    private byte[] img;


    public CarDTO getCarDTO(){
        CarDTO carDTO = new CarDTO();

        carDTO.setId(id);
        carDTO.setBrand(brand);
        carDTO.setName(name);
        carDTO.setColor(color);
        carDTO.setType(type);
        carDTO.setTransmission(transmission);
        carDTO.setDescription(description);
        carDTO.setPrice(price);
        carDTO.setYear(year);
//        carDTO.setReturnedImage(img);

        return carDTO;
    }
}

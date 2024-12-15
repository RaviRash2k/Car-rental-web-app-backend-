package com.example.Car.rental.web.entity;

import com.example.Car.rental.web.dto.BookACarDTO;
import com.example.Car.rental.web.enums.BookCarStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Entity
@Data
public class BookACar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date fromDate;
    private Date toDate;
    private Long days;
    private Long price;
    private BookCarStatus bookCarStatus;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "car_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Car car;


    public BookACarDTO getBookACarDTO(){

        BookACarDTO bookACarDTO = new BookACarDTO();

        bookACarDTO.setId(id);
        bookACarDTO.setDays(days);
        bookACarDTO.setFromDate(fromDate);
        bookACarDTO.setToDate(toDate);
        bookACarDTO.setPrice(price);
        bookACarDTO.setBookCarStatus(bookCarStatus);
        bookACarDTO.setUserId(user.getId());
        bookACarDTO.setCarId(car.getId());
        bookACarDTO.setUserName(user.getName());
        bookACarDTO.setEmail(user.getEmail());

        return bookACarDTO;
    }

}

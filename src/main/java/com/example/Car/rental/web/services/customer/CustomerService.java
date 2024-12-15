package com.example.Car.rental.web.services.customer;

import com.example.Car.rental.web.dto.BookACarDTO;
import com.example.Car.rental.web.dto.CarDTO;
import com.example.Car.rental.web.dto.CarDTOListDTO;
import com.example.Car.rental.web.dto.SearchCarDTO;

import java.util.List;

public interface CustomerService {

    List<CarDTO> getAllCars();

    boolean bookACar(BookACarDTO bookACarDTO);

    CarDTO getCarById(Long id);
    List<BookACarDTO> getBookingByUserId(Long userId);

    CarDTOListDTO searchCar(SearchCarDTO searchCarDTO);
}

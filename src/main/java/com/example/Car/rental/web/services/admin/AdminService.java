package com.example.Car.rental.web.services.admin;

import com.example.Car.rental.web.dto.BookACarDTO;
import com.example.Car.rental.web.dto.CarDTO;
import com.example.Car.rental.web.dto.CarDTOListDTO;
import com.example.Car.rental.web.dto.SearchCarDTO;

import java.util.List;

public interface AdminService {

    boolean postCar(CarDTO carDTO);

    List<CarDTO> getAllCars();

    void deleteCar(Long id);

    CarDTO getCarById(Long id);

    boolean updateCar(Long id, CarDTO carDTO);

    List<BookACarDTO> getBookings();

    boolean changeBookingStatus(Long bookingId, String Status);

    CarDTOListDTO searchCar(SearchCarDTO searchCarDTO);
}

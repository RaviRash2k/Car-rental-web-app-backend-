package com.example.Car.rental.web.controller;

import com.example.Car.rental.web.dto.BookACarDTO;
import com.example.Car.rental.web.dto.CarDTO;
import com.example.Car.rental.web.dto.SearchCarDTO;
import com.example.Car.rental.web.entity.Car;
import com.example.Car.rental.web.services.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/getAllCars")
    public ResponseEntity<List<CarDTO>> getAllCars(){
        List<CarDTO> carDTOList = customerService.getAllCars();

        return ResponseEntity.ok(carDTOList);
    }

    @PostMapping("/car/book")
    public ResponseEntity<Void> bookACar(@RequestBody BookACarDTO bookACarDTO){
        boolean success = customerService.bookACar(bookACarDTO);

        if(success)return ResponseEntity.status(HttpStatus.CREATED).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/car/{id}")
    public ResponseEntity<CarDTO> getCarById(@PathVariable Long id){
        CarDTO getCar = customerService.getCarById(id);

        if(getCar == null){
            return ResponseEntity.notFound().build();
        } return ResponseEntity.ok(getCar);
    }

    @GetMapping("/car/booking/{userId}")
    public ResponseEntity<List<BookACarDTO>> bookACarDTO(@PathVariable Long userId){
        return ResponseEntity.ok(customerService.getBookingByUserId(userId));
    }

    @PostMapping("/car/search")
    public ResponseEntity<?> searchCar(@RequestBody SearchCarDTO searchCarDTO){

        return ResponseEntity.ok(customerService.searchCar(searchCarDTO));
    }
}

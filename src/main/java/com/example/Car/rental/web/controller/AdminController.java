package com.example.Car.rental.web.controller;

import com.example.Car.rental.web.dto.BookACarDTO;
import com.example.Car.rental.web.dto.CarDTO;
import com.example.Car.rental.web.dto.SearchCarDTO;
import com.example.Car.rental.web.services.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/car")
    public ResponseEntity<?> postCar(@RequestBody CarDTO carDTO){

        boolean status = adminService.postCar(carDTO);
        if(status){
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/cars")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(adminService.getAllCars());
    }

    @DeleteMapping("/car/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id){
        adminService.deleteCar(id);

        return ResponseEntity.ok(null);
    }

    @GetMapping("/car/{id}")
    public ResponseEntity<CarDTO> getCarById(@PathVariable Long id){
        CarDTO carDTO = adminService.getCarById(id);
        return ResponseEntity.ok(carDTO);
    }

    @PutMapping("/car/{id}")
    public ResponseEntity<Void> updateCar(@PathVariable Long id, @ModelAttribute CarDTO carDTO)throws Exception{

        boolean success = adminService.updateCar(id, carDTO);

        try{
            if(success)
                return ResponseEntity.status(HttpStatus.OK).build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/car/bookings")
    public ResponseEntity<List<BookACarDTO>> getBookings(){
        return ResponseEntity.ok(adminService.getBookings());
    }

    @GetMapping("/car/booking/{bookingId}/{status}")
    public ResponseEntity<?> changeBookingStatus(@PathVariable Long bookingId, @PathVariable String status){
        boolean success = adminService.changeBookingStatus(bookingId, status);

        if(success) return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/car/search")
    public ResponseEntity<?> searchCar(@RequestBody SearchCarDTO searchCarDTO){
        return ResponseEntity.ok(adminService.searchCar(searchCarDTO));
    }
}

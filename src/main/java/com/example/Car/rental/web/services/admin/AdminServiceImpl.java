package com.example.Car.rental.web.services.admin;

import com.example.Car.rental.web.dto.BookACarDTO;
import com.example.Car.rental.web.dto.CarDTO;
import com.example.Car.rental.web.dto.CarDTOListDTO;
import com.example.Car.rental.web.dto.SearchCarDTO;
import com.example.Car.rental.web.entity.BookACar;
import com.example.Car.rental.web.entity.Car;
import com.example.Car.rental.web.enums.BookCarStatus;
import com.example.Car.rental.web.repo.BookACarRepo;
import com.example.Car.rental.web.repo.CarRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

    private final CarRepo carRepo;
    private final BookACarRepo bookACarRepo;

    //post car
    @Override
    public boolean postCar(CarDTO carDTO) {

        try{
            Car car = new Car();
            car.setName(carDTO.getName());
            car.setBrand(carDTO.getBrand());
            car.setColor(carDTO.getColor());
            car.setType(carDTO.getType());
            car.setTransmission(carDTO.getTransmission());
            car.setDescription(carDTO.getDescription());
            car.setPrice(carDTO.getPrice());
            car.setYear(carDTO.getYear());
//            car.setImg(carDTO.getImg().getBytes());
            carRepo.save(car);
            return true;

        }catch (Exception e){
            return false;
        }
    }

    //get all cars

    @Override
    public List<CarDTO> getAllCars() {
        return carRepo.findAll().stream().map(Car::getCarDTO).collect(Collectors.toList());
    }


    //delete car
    @Override
    public void deleteCar(Long id) {
        carRepo.deleteById(id);
    }

    @Override
    public CarDTO getCarById(Long id) {
        Optional<Car> optionalCar = carRepo.findById(id);
        return optionalCar.map(Car::getCarDTO).orElse(null);
    }

    @Override
    public boolean updateCar(Long id, CarDTO carDTO) {
        Optional<Car> optionalCar = carRepo.findById(id);

        if(optionalCar.isPresent()){
            Car exsistingCar = optionalCar.get();
            exsistingCar.setName(carDTO.getName());
            exsistingCar.setBrand(carDTO.getBrand());
            exsistingCar.setColor(carDTO.getColor());
            exsistingCar.setType(carDTO.getType());
            exsistingCar.setPrice(carDTO.getPrice());
            exsistingCar.setYear(carDTO.getYear());
            exsistingCar.setDescription(carDTO.getDescription());
            exsistingCar.setTransmission(carDTO.getTransmission());
            carRepo.save(exsistingCar);
            return true;

        }else{
            return false;
        }
    }

    @Override
    public List<BookACarDTO> getBookings() {
        return bookACarRepo.findAll().stream().map(BookACar::getBookACarDTO).collect(Collectors.toList());
    }

    @Override
    public boolean changeBookingStatus(Long bookingId, String status) {
        Optional<BookACar> booking = bookACarRepo.findById(bookingId);

        if (booking.isPresent()){

            BookACar existingBookACar = booking.get();

            if(Objects.equals(status, "approve"))
                existingBookACar.setBookCarStatus(BookCarStatus.APPROVED);
            else
                existingBookACar.setBookCarStatus(BookCarStatus.REJECTED);

            bookACarRepo.save(existingBookACar);

            return true;
        }
        return false;
    }

    @Override
    public CarDTOListDTO searchCar(SearchCarDTO searchCarDTO) {

        Car car = new Car();
        car.setBrand(searchCarDTO.getBrand());
        car.setType(searchCarDTO.getType());
        car.setTransmission(searchCarDTO.getTransmission());
        car.setColor(searchCarDTO.getColor());

        ExampleMatcher exampleMatcher =
                ExampleMatcher.matchingAll()
                        .withMatcher("brand",ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                        .withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                        .withMatcher("transmission", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                        .withMatcher("color", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

        Example<Car> carExample = Example.of(car,exampleMatcher);
        List<Car> carList = carRepo.findAll(carExample);

        CarDTOListDTO carDTOListDTO = new CarDTOListDTO();
        carDTOListDTO.setCarDTOList(carList.stream().map(Car::getCarDTO).collect(Collectors.toList()));

        return carDTOListDTO;
    }
}

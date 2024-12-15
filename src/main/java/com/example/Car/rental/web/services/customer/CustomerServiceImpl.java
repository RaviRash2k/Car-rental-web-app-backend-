package com.example.Car.rental.web.services.customer;

import com.example.Car.rental.web.dto.BookACarDTO;
import com.example.Car.rental.web.dto.CarDTO;
import com.example.Car.rental.web.dto.CarDTOListDTO;
import com.example.Car.rental.web.dto.SearchCarDTO;
import com.example.Car.rental.web.entity.BookACar;
import com.example.Car.rental.web.entity.Car;
import com.example.Car.rental.web.entity.User;
import com.example.Car.rental.web.enums.BookCarStatus;
import com.example.Car.rental.web.repo.BookACarRepo;
import com.example.Car.rental.web.repo.CarRepo;
import com.example.Car.rental.web.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CarRepo carRepo;
    private final UserRepo userRepo;
    private final BookACarRepo bookACarRepo;
    @Override
    public List<CarDTO> getAllCars() {
        return carRepo.findAll().stream().map(Car::getCarDTO).collect(Collectors.toList());
    }

    @Override
    public boolean bookACar(BookACarDTO bookACarDTO) {

        Optional<Car> optionalCar = carRepo.findById(bookACarDTO.getCarId());
        Optional<User> optionalUser = userRepo.findById(bookACarDTO.getUserId());

        if(optionalCar.isPresent() && optionalUser.isPresent()){

            BookACar bookACar = new BookACar();

            Car car = optionalCar.get();

            bookACar.setUser(optionalUser.get());
            bookACar.setCar(car);
            bookACar.setBookCarStatus(BookCarStatus.PENDING);
            long diffInMilliSeconds = bookACarDTO.getToDate().getTime() - bookACar.getFromDate().getTime();
            long days = TimeUnit.MICROSECONDS.toDays(diffInMilliSeconds);

            bookACar.setDays(days);
            bookACar.setPrice(car.getPrice() * days);

            bookACarRepo.save(bookACar);

            return true;
        }

        return false;
    }

    @Override
    public CarDTO getCarById(Long id) {
        Optional<Car> optionalCar = carRepo.findById(id);
        return optionalCar.map(Car::getCarDTO).orElse(null);
    }

    @Override
    public List<BookACarDTO> getBookingByUserId(Long userId) {
        return bookACarRepo.findAllByUserId(userId).stream().map(BookACar::getBookACarDTO).collect(Collectors.toList());
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
                        .withMatcher("brand", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                        .withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                        .withMatcher("transmission", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                        .withMatcher("color", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

        Example<Car> carExample = Example.of(car, exampleMatcher);
        List<Car> carList = carRepo.findAll(carExample);

        CarDTOListDTO carDTOListDTO = new CarDTOListDTO();
        carDTOListDTO.setCarDTOList(carList.stream().map(Car::getCarDTO).collect(Collectors.toList()));

        return carDTOListDTO;
    }
}

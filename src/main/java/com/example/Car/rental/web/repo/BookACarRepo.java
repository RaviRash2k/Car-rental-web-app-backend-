package com.example.Car.rental.web.repo;

import com.example.Car.rental.web.dto.BookACarDTO;
import com.example.Car.rental.web.entity.BookACar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookACarRepo extends JpaRepository<BookACar, Long> {
    List<BookACar> findAllByUserId(Long userId);
}

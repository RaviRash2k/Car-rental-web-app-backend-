package com.example.Car.rental.web.repo;

import com.example.Car.rental.web.entity.User;
import com.example.Car.rental.web.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {

    Optional<User> findFirstByEmail(String email);

    User findByUserRole(UserRole userRole);
}

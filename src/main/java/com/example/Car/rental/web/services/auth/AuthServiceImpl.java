package com.example.Car.rental.web.services.auth;

import com.example.Car.rental.web.dto.SignupRequest;
import com.example.Car.rental.web.dto.UserDTO;
import com.example.Car.rental.web.entity.User;
import com.example.Car.rental.web.enums.UserRole;
import com.example.Car.rental.web.repo.UserRepo;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepo userRepo;

    @PostConstruct
    public void createAdminAccount(){
        User adminAcc = userRepo.findByUserRole(UserRole.ADMIN);

        if(adminAcc == null){

            User newAdmin = new User();
            newAdmin.setName("Admin");
            newAdmin.setEmail("admin@test.com");
            newAdmin.setPassword(new BCryptPasswordEncoder().encode("admin"));
            newAdmin.setUserRole(UserRole.ADMIN);
            userRepo.save(newAdmin);
            System.out.println("Admin acc created!");
        }
    }

    //Signup user
    @Override
    public UserDTO createUser(SignupRequest signupRequest) {

        User user = new User();
        user.setName(signupRequest.getName());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        user.setUserRole(UserRole.CUSTOMER);

        User createdUser = userRepo.save(user);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(createdUser.getId());

        return userDTO;
    }

    //Email already in DB
    @Override
    public Boolean hasUserEmail(String email) {

        return userRepo.findFirstByEmail(email).isPresent();
    }
}

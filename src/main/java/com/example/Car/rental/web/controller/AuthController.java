package com.example.Car.rental.web.controller;

import com.example.Car.rental.web.dto.AuthenticationRequest;
import com.example.Car.rental.web.dto.AuthenticationResponse;
import com.example.Car.rental.web.dto.SignupRequest;
import com.example.Car.rental.web.dto.UserDTO;
import com.example.Car.rental.web.entity.User;
import com.example.Car.rental.web.repo.UserRepo;
import com.example.Car.rental.web.services.auth.AuthService;
import com.example.Car.rental.web.services.jwt.UserService;
import com.example.Car.rental.web.utills.JWTUtill;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor

public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JWTUtill jwtUtill;
    private final UserRepo userRepo;

    @PostMapping("/signup")
    public ResponseEntity<?> signupCustomer(@RequestBody SignupRequest signupRequest){

        if(authService.hasUserEmail(signupRequest.getEmail())){
            return new ResponseEntity<>("Already have in email", HttpStatus.NOT_ACCEPTABLE);
        }

        UserDTO createCusDTO = authService.createUser(signupRequest);
        if(createCusDTO == null){
            return new ResponseEntity<>("Signup fail", HttpStatus.BAD_REQUEST);

        }else{
            return new ResponseEntity<>(createCusDTO, HttpStatus.CREATED);
        }
    }

    @PostMapping("/login")
    public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)throws
            BadCredentialsException,
            DisabledException,
            UsernameNotFoundException{

        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getEmail(),
                    authenticationRequest.getPassword()));

        }catch (BadCredentialsException e){
            throw new BadCredentialsException("Incorrect user name or password");
        }
        final UserDetails userDetails = userService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());
        Optional<User> optionalUser = userRepo.findFirstByEmail(userDetails.getUsername());
        final String jwt = jwtUtill.generateToken(userDetails);

        AuthenticationResponse authenticationResponse = new AuthenticationResponse();

        if(optionalUser.isPresent()){
            authenticationResponse.setJwt(jwt);
            authenticationResponse.setUserID((long) optionalUser.get().getId());
            authenticationResponse.setUserRole(optionalUser.get().getUserRole());
        }

        return authenticationResponse;
    }
}

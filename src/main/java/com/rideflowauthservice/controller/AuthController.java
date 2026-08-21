package com.rideflowauthservice.controller;


import com.rideflowauthservice.dto.passenger.PassengerResponseDTO;
import com.rideflowauthservice.dto.passenger.PassengerSignupRequest;
import com.rideflowauthservice.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    @PostMapping("/signUp")
    public ResponseEntity<PassengerResponseDTO> signUp(@RequestBody PassengerSignupRequest passengerSignupRequestDto){
        PassengerResponseDTO passengerResponseDTO=authService.signUpPassenger(passengerSignupRequestDto);
       return new ResponseEntity<>(passengerResponseDTO,HttpStatus.CREATED);
    }
}

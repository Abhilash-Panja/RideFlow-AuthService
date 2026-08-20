package com.rideflowauthservice.controller;

import com.rideflowauthservice.dto.PassengerSignupRequest;
import com.rideflowauthservice.dto.PassengerSignupRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/auth")
public class AuthController {
    public ResponseEntity<?> signUp(@RequestBody PassengerSignupRequest passengerSignupRequestDto){

        return ResponseEntity.status(HttpStatus.OK).body()
    }
}

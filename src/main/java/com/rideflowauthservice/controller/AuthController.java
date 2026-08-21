package com.rideflowauthservice.controller;

import com.rideflowauthservice.dto.auth.PassengerLoginRequest;
import com.rideflowauthservice.dto.auth.PassengerLoginResponse;
import com.rideflowauthservice.dto.passenger.PassengerResponseDTO;
import com.rideflowauthservice.dto.auth.PassengerSignupRequest;
import com.rideflowauthservice.security.PassengerPrinciple;
import com.rideflowauthservice.service.AuthService;
import com.rideflowauthservice.service.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Value("${cookie.expiration-ms}")
    private long cookieExpiry;

    @PostMapping("/signUp")
    public ResponseEntity<PassengerResponseDTO> signUp(@RequestBody PassengerSignupRequest passengerSignupRequestDto){
        PassengerResponseDTO passengerResponseDTO=authService.signUpPassenger(passengerSignupRequestDto);
       return new ResponseEntity<>(passengerResponseDTO,HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<PassengerLoginResponse>login(@RequestBody PassengerLoginRequest passengerLoginRequest, HttpServletResponse servletResponse){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(passengerLoginRequest.getEmail(), passengerLoginRequest.getPassword())
        );

        PassengerPrinciple passengerPrinciple = (PassengerPrinciple) authentication.getPrincipal();
        assert passengerPrinciple != null;
        String token = jwtService.generateToken(passengerPrinciple);
        /*
        * Creating the Cookie and sending jwt_token as cookie in header
        * */
        ResponseCookie responseCookie= ResponseCookie.from("Jwt_Token",token)
                        .secure(false)
                        .httpOnly(true)
                        .path("/")
                        .maxAge(cookieExpiry)
                        .build();
        servletResponse.setHeader(HttpHeaders.SET_COOKIE,responseCookie.toString());
        return ResponseEntity.status(HttpStatus.OK).body(new PassengerLoginResponse(token));
    }
}

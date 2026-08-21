package com.rideflowauthservice.service;


import com.rideflowauthservice.dto.passenger.PassengerSignupRequest;
import com.rideflowauthservice.dto.passenger.PassengerResponseDTO;
import com.rideflowauthservice.mapper.PassengerMapper;
import com.rideflowauthservice.models.Passenger;
import com.rideflowauthservice.repositories.PassengerRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PassengerRepository passengerRepository;
    private final PasswordEncoder passwordEncoder;
    public PassengerResponseDTO signUpPassenger(PassengerSignupRequest signupRequest){
        Passenger passenger=Passenger.builder()
                .passengerName(signupRequest.getName())
                .email(signupRequest.getEmail())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .phoneNumber(signupRequest.getPhoneNumber())
                .build();
         passengerRepository.save(passenger);
        return PassengerMapper.toResponseDTO(passenger);

    }
}

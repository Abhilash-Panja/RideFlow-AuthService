package com.rideflowauthservice.service;


import com.rideflow.rideflowentityservice.models.Passenger;
import com.rideflow.rideflowentityservice.models.Role;
import com.rideflowauthservice.exception.EmailAlreadyRegisteredException;
import com.rideflowauthservice.dto.auth.PassengerSignupRequest;
import com.rideflowauthservice.dto.passenger.PassengerResponseDTO;
import com.rideflowauthservice.mapper.PassengerMapper;
import com.rideflowauthservice.repositories.PassengerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PassengerRepository passengerRepository;
    private final PasswordEncoder passwordEncoder;

    public PassengerResponseDTO signUpPassenger(PassengerSignupRequest signupRequest){
        if (passengerRepository.existsByEmail(signupRequest.getEmail())) {
            throw new EmailAlreadyRegisteredException(signupRequest.getEmail());
        }

        Passenger passenger=Passenger.builder()
                .passengerName(signupRequest.getName())
                .email(signupRequest.getEmail())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .phoneNumber(signupRequest.getPhoneNumber())
                .role(Role.PASSENGER)
                .build();
         passengerRepository.save(passenger);
        return PassengerMapper.toResponseDTO(passenger);
    }

}

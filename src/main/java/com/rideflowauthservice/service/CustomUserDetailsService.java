package com.rideflowauthservice.service;

import com.rideflow.rideflowentityservice.models.Passenger;
import com.rideflowauthservice.exception.EmailNotFoundException;
import com.rideflowauthservice.repositories.PassengerRepository;
import com.rideflowauthservice.security.PassengerPrinciple;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/*
 * CustomUserDetailsService (tells Spring how to load a user by username during login)
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final PassengerRepository passengerRepository;

    public CustomUserDetailsService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(@NonNull String email) throws UsernameNotFoundException {
        Passenger passenger = passengerRepository.findPassengerByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException("Passenger not found with this Email: " + email));
        return new PassengerPrinciple(passenger);
    }
}

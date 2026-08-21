package com.rideflowauthservice.repositories;


import com.rideflowauthservice.models.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PassengerRepository extends JpaRepository<Passenger,Long> {
    Optional<Passenger> findPassengerByEmail(String email);
}

package org.example.uberreviewservice.repository;

import org.example.uberreviewservice.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerRepository extends JpaRepository<Passenger,Long> {
}

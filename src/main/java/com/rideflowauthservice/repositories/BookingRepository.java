package org.example.uberreviewservice.repository;

import org.example.uberreviewservice.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking,Long> {
    long countByPassengerId(Long passengerId);
    long countByDriverId(Long driverId);
}

package com.rideflowauthservice.repositories;


import com.rideflowauthservice.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking,Long> {
    long countByPassengerId(Long passengerId);
    long countByDriverId(Long driverId);
}

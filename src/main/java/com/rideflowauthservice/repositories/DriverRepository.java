package org.example.uberreviewservice.repository;

import org.example.uberreviewservice.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver,Long> {
    boolean existsByLicenceNumber(String licenceNumber);
}

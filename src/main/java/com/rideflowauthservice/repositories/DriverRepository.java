package com.rideflowauthservice.repositories;


import com.rideflowauthservice.models.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver,Long> {
    boolean existsByLicenceNumber(String licenceNumber);
}

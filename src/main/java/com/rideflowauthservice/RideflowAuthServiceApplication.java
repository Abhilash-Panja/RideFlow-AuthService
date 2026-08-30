package com.rideflowauthservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;



@SpringBootApplication
@EntityScan(basePackages = "com.rideflow.rideflowentityservice.models")
public class RideflowAuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RideflowAuthServiceApplication.class, args);
    }

}

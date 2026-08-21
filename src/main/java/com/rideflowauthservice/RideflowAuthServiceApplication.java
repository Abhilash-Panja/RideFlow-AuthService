package com.rideflowauthservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing
public class RideflowAuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RideflowAuthServiceApplication.class, args);
    }

}

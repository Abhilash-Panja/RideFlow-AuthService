package com.rideflowauthservice.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


import java.time.LocalTime;


@Entity
@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Booking extends BaseModel{

    private LocalTime startTime;
    private LocalTime endTime;
    private long totalDistance;
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;;
    @ManyToOne
    private Driver driver;
    @ManyToOne
    private  Passenger passenger;


}

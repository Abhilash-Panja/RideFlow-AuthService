package com.rideflowauthservice.dto.booking;

import com.rideflow.rideflowentityservice.models.BookingStatus;
import com.rideflowauthservice.dto.driver.DriverSummaryDTO;
import com.rideflowauthservice.dto.passenger.PassengerSummaryDTO;
import com.rideflowauthservice.dto.review.ReviewSummaryDTO;

import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponseDTO {
    private Long id;
    private LocalTime startTime;
    private LocalTime endTime;
    private long totalDistance;
    private BookingStatus bookingStatus;

    private DriverSummaryDTO driver;
    private PassengerSummaryDTO passenger;
    private ReviewSummaryDTO review; // null until the ride is reviewed
}

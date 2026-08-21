package com.rideflowauthservice.dto.passenger;

import lombok.*;

// Embedded inside BookingResponseDTO — deliberately minimal
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PassengerSummaryDTO {
    private Long id;
    private String passengerName;
}

package com.rideflowauthservice.dto.passenger;

import lombok.*;

// Used when registering a new passenger
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PassengerRequestDTO {
    private String passengerName;
}
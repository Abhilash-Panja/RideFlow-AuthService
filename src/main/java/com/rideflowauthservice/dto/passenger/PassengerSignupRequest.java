package com.rideflowauthservice.dto.passenger;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PassengerSignupRequest {
    private String email;

    private String password;

    private String phoneNumber;

    private String name;

}

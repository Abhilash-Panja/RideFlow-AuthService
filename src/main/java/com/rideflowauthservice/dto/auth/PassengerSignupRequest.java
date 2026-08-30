package com.rideflowauthservice.dto.auth;


import com.rideflow.rideflowentityservice.models.Role;
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

    private Role role;

}

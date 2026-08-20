package com.rideflowauthservice.dto;

import lombok.*;

import java.util.Date;
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

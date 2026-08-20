package com.rideflowauthservice.dto;

import lombok.*;

import java.util.Date;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PassengerResponse {
    private String id;

    private String name;

    private String email;

    private String password; // encrypted password

    private String phoneNumber;

    private Date createdAt;
}

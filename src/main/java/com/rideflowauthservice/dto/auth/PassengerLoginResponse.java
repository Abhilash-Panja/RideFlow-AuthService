package com.rideflowauthservice.dto.auth;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PassengerLoginResponse {
    private String token;
}

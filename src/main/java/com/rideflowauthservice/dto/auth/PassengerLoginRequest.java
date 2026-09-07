package com.rideflowauthservice.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PassengerLoginRequest {
    @NotBlank(message = "Email cannot be blank")
    @Schema(description = "Credential lookup input. @NotBlank exists but the controller does not use @Valid, so it does not produce DTO validation errors here.", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "anita.rideflow@example.com")
    private String email;
    @NotBlank(message = "Password cannot be blank")
    @Schema(description = "Password checked by AuthenticationManager/BCrypt if the request reaches the controller. @NotBlank is not activated by @Valid.", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "RideFlowDemo!2026")
    private String password;
}

package com.rideflowauthservice.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;


import com.rideflow.rideflowentityservice.models.Role;
import lombok.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PassengerSignupRequest {
    @Schema(description = "Used as the lookup key and JWT subject. No @Valid, format check, or duplicate-email check in the controller/service. Current shared entity declares it non-null.", requiredMode = Schema.RequiredMode.REQUIRED, example = "anita.rideflow@example.com")
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @Schema(description = "BCrypt input; null fails before persistence. Current shared entity requires a value. No DTO length rule.", requiredMode = Schema.RequiredMode.REQUIRED, example = "RideFlowDemo!2026")
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must contain at least 8 characters")
    private String password;

    @Schema(description = "Copied to Passenger; current shared entity requires a value. No format validation.", requiredMode = Schema.RequiredMode.REQUIRED, example = "9000000001")
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @Schema(description = "Mapped to Passenger.passengerName, not a field named name in the response. Current shared entity requires it.", requiredMode = Schema.RequiredMode.REQUIRED, example = "Anita Sharma")
    @NotBlank(message = "Name is required")
    private String name;

    @Schema(description = "Client value is copied directly. PASSENGER, DRIVER and ADMIN exist in the inspected shared source. Use PASSENGER for this exercise; no server-side restriction is implemented.", requiredMode = Schema.RequiredMode.REQUIRED, example = "PASSENGER")
    @NotNull(message = "Role is required")
    private Role role;

}

package org.example.uberreviewservice.dto.booking;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.example.uberreviewservice.model.BookingStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingStatusUpdateDTO {
    @NotNull(message = "newStatus is required")
    private BookingStatus newStatus;
}

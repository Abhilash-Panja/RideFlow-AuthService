package com.rideflowauthservice.dto.booking;

import com.rideflowauthservice.models.BookingStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingStatusUpdateDTO {
    @NotNull(message = "newStatus is required")
    private BookingStatus newStatus;
}

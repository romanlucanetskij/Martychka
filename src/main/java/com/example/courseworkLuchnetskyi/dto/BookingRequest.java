package com.example.courseworkLuchnetskyi.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record BookingRequest(
        @NotNull Long guestId,
        @NotNull Long roomId,
        @NotNull LocalDate checkInDate,
        @NotNull @Future LocalDate checkOutDate
) {
}

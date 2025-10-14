package com.example.courseworkLuchnetskyi.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PaymentRequest(
        @NotNull Long bookingId,
        @NotNull BigDecimal amount,
        @NotNull LocalDate paymentDate
) {
}

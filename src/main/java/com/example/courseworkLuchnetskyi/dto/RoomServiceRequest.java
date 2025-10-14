package com.example.courseworkLuchnetskyi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record RoomServiceRequest(
        @NotNull Long roomId,
        @NotBlank String description,
        @NotNull BigDecimal price
) {
}

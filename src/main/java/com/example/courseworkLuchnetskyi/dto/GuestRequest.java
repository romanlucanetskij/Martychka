package com.example.courseworkLuchnetskyi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record GuestRequest(
        @NotBlank String name,
        @NotBlank String phone,
        @Email @NotBlank String email
) {
}

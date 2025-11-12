package org.castle.djames.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record CustomerRequest(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank LocalDate birtDate,
        @NotBlank @Email String email,
        @NotBlank String phone,
        @NotBlank String address) {
}

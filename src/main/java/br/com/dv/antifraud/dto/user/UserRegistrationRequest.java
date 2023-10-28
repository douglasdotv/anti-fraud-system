package br.com.dv.antifraud.dto.user;

import jakarta.validation.constraints.NotBlank;

public record UserRegistrationRequest(
        @NotBlank(message = "Name is required.")
        String name,
        @NotBlank(message = "Username is required.")
        String username,
        @NotBlank(message = "Password is required.")
        String password
) {
}

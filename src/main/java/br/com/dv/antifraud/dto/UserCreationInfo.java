package br.com.dv.antifraud.dto;

import jakarta.validation.constraints.NotBlank;

public record UserCreationInfo(
        @NotBlank(message = "Name is required.")
        String name,
        @NotBlank(message = "Username is required.")
        String username,
        @NotBlank(message = "Password is required.")
        String password
) {
}

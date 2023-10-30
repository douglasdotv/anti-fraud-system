package br.com.dv.antifraud.dto.user;

import jakarta.validation.constraints.NotBlank;

import static br.com.dv.antifraud.constants.AntifraudSystemConstants.NAME_REQUIRED_MESSAGE;
import static br.com.dv.antifraud.constants.AntifraudSystemConstants.USERNAME_REQUIRED_MESSAGE;
import static br.com.dv.antifraud.constants.AntifraudSystemConstants.PASSWORD_REQUIRED_MESSAGE;

public record UserRegistrationRequest(
        @NotBlank(message = NAME_REQUIRED_MESSAGE)
        String name,
        @NotBlank(message = USERNAME_REQUIRED_MESSAGE)
        String username,
        @NotBlank(message = PASSWORD_REQUIRED_MESSAGE)
        String password
) {
}

package br.com.dv.antifraud.dto.user;

import br.com.dv.antifraud.enums.UserOperation;
import br.com.dv.antifraud.validation.EnumValue;
import jakarta.validation.constraints.NotBlank;

import static br.com.dv.antifraud.constants.AntifraudSystemConstants.INVALID_USER_OPERATION_MESSAGE;

public record UserStatusUpdateRequest(
        @NotBlank(message = "Username is required.")
        String username,
        @EnumValue(enumClass = UserOperation.class, message = INVALID_USER_OPERATION_MESSAGE)
        UserOperation operation
) {
}

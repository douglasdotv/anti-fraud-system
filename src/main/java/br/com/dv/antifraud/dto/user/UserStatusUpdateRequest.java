package br.com.dv.antifraud.dto.user;

import br.com.dv.antifraud.enums.UserOperation;
import br.com.dv.antifraud.validation.EnumValue;
import jakarta.validation.constraints.NotBlank;

public record UserStatusUpdateRequest(
        @NotBlank(message = "Username is required.")
        String username,
        @EnumValue(
                enumClass = UserOperation.class,
                message = "Invalid operation. The available operations are LOCK and UNLOCK.")
        UserOperation operation
) {
}

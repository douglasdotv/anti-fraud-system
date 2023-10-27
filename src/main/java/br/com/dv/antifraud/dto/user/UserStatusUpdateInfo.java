package br.com.dv.antifraud.dto.user;

import br.com.dv.antifraud.enums.UserOperation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserStatusUpdateInfo(
        @NotBlank(message = "Username is required.")
        String username,
        @NotNull(message = "Invalid operation. The available operations are LOCK and UNLOCK.")
        UserOperation operation
) {
}

package br.com.dv.antifraud.dto;

import br.com.dv.antifraud.enums.RoleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRoleUpdateInfo(
        @NotBlank(message = "Username is required.")
        String username,
        @NotNull(message = "Invalid role. The available roles are MERCHANT and SUPPORT.")
        RoleType role
) {
}
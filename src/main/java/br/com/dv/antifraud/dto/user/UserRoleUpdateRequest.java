package br.com.dv.antifraud.dto.user;

import br.com.dv.antifraud.enums.RoleType;
import br.com.dv.antifraud.validation.EnumValue;
import jakarta.validation.constraints.NotBlank;

public record UserRoleUpdateRequest(
        @NotBlank(message = "Username is required.")
        String username,
        @EnumValue(
                enumClass = RoleType.class,
                message = "Invalid role. The available roles are MERCHANT and SUPPORT.")
        RoleType role
) {
}

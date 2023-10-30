package br.com.dv.antifraud.dto.ip;

import jakarta.validation.constraints.Pattern;

import static br.com.dv.antifraud.constants.AntifraudSystemConstants.INVALID_IPV4_MESSAGE;
import static br.com.dv.antifraud.constants.AntifraudSystemConstants.IPV4_REGEX;

public record SuspiciousIpRequest(
        @Pattern(regexp = IPV4_REGEX, message = INVALID_IPV4_MESSAGE)
        String ip
) {
}

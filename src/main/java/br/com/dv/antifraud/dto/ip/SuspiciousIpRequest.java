package br.com.dv.antifraud.dto.ip;

import jakarta.validation.constraints.Pattern;

public record SuspiciousIpRequest(
        @Pattern(regexp = "^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)(\\.(?!$)|$)){4}$", message = "Invalid IPv4 format.")
        String ip
) {
}

package br.com.dv.antifraud.dto;

import br.com.dv.antifraud.validation.CardNumber;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record TransactionInfo(
        @NotNull(message = "Amount is required.")
        @Positive(message = "Amount must be positive.")
        Long amount,
        @Pattern(regexp = "^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)(\\.(?!$)|$)){4}$", message = "Invalid IPv4 format.")
        String ip,
        @CardNumber
        String number
) {
}

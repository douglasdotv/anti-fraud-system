package br.com.dv.antifraud.dto.transaction;

import br.com.dv.antifraud.enums.WorldRegion;
import br.com.dv.antifraud.validation.CardNumber;
import br.com.dv.antifraud.validation.EnumValue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record TransactionRequest(
        @NotNull(message = "Amount is required.")
        @Positive(message = "Amount must be positive.")
        Long amount,
        @Pattern(regexp = "^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)(\\.(?!$)|$)){4}$", message = "Invalid IPv4 format.")
        String ip,
        @CardNumber
        String number,
        @EnumValue(
                enumClass = WorldRegion.class,
                message = "Invalid region. The available regions are: EAP, ECA, HIC, LAC, MENA, SA and SSA.")
        WorldRegion region,
        @PastOrPresent(message = "Date must be in the past or present.")
        LocalDateTime date
) {
}

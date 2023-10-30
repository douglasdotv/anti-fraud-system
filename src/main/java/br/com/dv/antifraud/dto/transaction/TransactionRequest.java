package br.com.dv.antifraud.dto.transaction;

import br.com.dv.antifraud.enums.WorldRegion;
import br.com.dv.antifraud.validation.CardNumber;
import br.com.dv.antifraud.validation.EnumValue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

import static br.com.dv.antifraud.constants.AntifraudSystemConstants.AMOUNT_REQUIRED_MESSAGE;
import static br.com.dv.antifraud.constants.AntifraudSystemConstants.AMOUNT_MUST_BE_POSITIVE_MESSAGE;
import static br.com.dv.antifraud.constants.AntifraudSystemConstants.IPV4_REGEX;
import static br.com.dv.antifraud.constants.AntifraudSystemConstants.INVALID_IPV4_MESSAGE;
import static br.com.dv.antifraud.constants.AntifraudSystemConstants.INVALID_REGION_MESSAGE;
import static br.com.dv.antifraud.constants.AntifraudSystemConstants.DATE_CANNOT_BE_NULL_MESSAGE;
import static br.com.dv.antifraud.constants.AntifraudSystemConstants.DATE_PAST_OR_PRESENT_MESSAGE;

public record TransactionRequest(
        @NotNull(message = AMOUNT_REQUIRED_MESSAGE)
        @Positive(message = AMOUNT_MUST_BE_POSITIVE_MESSAGE)
        Long amount,
        @Pattern(regexp = IPV4_REGEX, message = INVALID_IPV4_MESSAGE)
        String ip,
        @CardNumber
        String number,
        @EnumValue(enumClass = WorldRegion.class, message = INVALID_REGION_MESSAGE)
        WorldRegion region,
        @NotNull(message = DATE_CANNOT_BE_NULL_MESSAGE)
        @PastOrPresent(message = DATE_PAST_OR_PRESENT_MESSAGE)
        LocalDateTime date
) {
}

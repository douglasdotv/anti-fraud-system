package br.com.dv.antifraud.dto.transaction;

import br.com.dv.antifraud.enums.TransactionResult;
import br.com.dv.antifraud.validation.EnumValue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import static br.com.dv.antifraud.constants.AntifraudSystemConstants.ID_NOT_NULL_MESSAGE;
import static br.com.dv.antifraud.constants.AntifraudSystemConstants.ID_MUST_BE_POSITIVE_MESSAGE;
import static br.com.dv.antifraud.constants.AntifraudSystemConstants.INVALID_FEEDBACK_MESSAGE;

public record FeedbackRequest(
        @NotNull(message = ID_NOT_NULL_MESSAGE)
        @Positive(message = ID_MUST_BE_POSITIVE_MESSAGE)
        Long transactionId,
        @EnumValue(enumClass = TransactionResult.class, message = INVALID_FEEDBACK_MESSAGE)
        TransactionResult feedback
) {
}

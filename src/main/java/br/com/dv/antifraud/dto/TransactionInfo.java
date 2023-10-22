package br.com.dv.antifraud.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TransactionInfo(
        @NotNull(message = "Amount is required.")
        @Positive(message = "Amount must be positive.")
        Long amount
) {
}

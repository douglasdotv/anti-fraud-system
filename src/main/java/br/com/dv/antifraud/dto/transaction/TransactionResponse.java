package br.com.dv.antifraud.dto.transaction;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record TransactionResponse(
        Long transactionId,
        Long amount,
        String ip,
        String number,
        String region,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime date,
        String result,
        String feedback
) {
}

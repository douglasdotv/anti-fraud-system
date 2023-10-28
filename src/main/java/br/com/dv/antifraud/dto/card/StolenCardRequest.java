package br.com.dv.antifraud.dto.card;

import br.com.dv.antifraud.validation.CardNumber;

public record StolenCardRequest(
        @CardNumber
        String number
) {
}

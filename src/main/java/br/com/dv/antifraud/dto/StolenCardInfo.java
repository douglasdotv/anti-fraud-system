package br.com.dv.antifraud.dto;

import br.com.dv.antifraud.validation.CardNumber;

public record StolenCardInfo(
        @CardNumber
        String number
) {
}

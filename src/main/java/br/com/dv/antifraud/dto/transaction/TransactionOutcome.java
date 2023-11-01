package br.com.dv.antifraud.dto.transaction;

import br.com.dv.antifraud.enums.TransactionResult;

public record TransactionOutcome(TransactionResult result, String info) {
}

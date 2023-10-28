package br.com.dv.antifraud.dto.transaction;

import br.com.dv.antifraud.enums.TransactionResult;

public record TransactionResponse(TransactionResult result, String info) {
}

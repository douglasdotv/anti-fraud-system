package br.com.dv.antifraud.enums;

import lombok.Getter;

@Getter
public enum TransactionInfo {

    AMOUNT("amount"),
    CARD_NUMBER("card-number"),
    IP("ip"),
    NONE("none");

    private final String value;

    TransactionInfo(String value) {
        this.value = value;
    }

}

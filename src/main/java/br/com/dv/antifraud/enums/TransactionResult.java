package br.com.dv.antifraud.enums;

public enum TransactionResult {

    ALLOWED, MANUAL_PROCESSING, PROHIBITED;

    public static TransactionResult fromAmount(Long amount) {
        if (amount <= 200) {
            return ALLOWED;
        } else if (amount <= 1500) {
            return MANUAL_PROCESSING;
        } else {
            return PROHIBITED;
        }
    }

}

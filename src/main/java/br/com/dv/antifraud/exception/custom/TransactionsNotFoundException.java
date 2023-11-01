package br.com.dv.antifraud.exception.custom;

public class TransactionsNotFoundException extends RuntimeException {

    public TransactionsNotFoundException(String message) {
        super(message);
    }

}

package br.com.dv.antifraud.exception;

public class StolenCardAlreadyExistsException extends RuntimeException {

    public StolenCardAlreadyExistsException(String message) {
        super(message);
    }

}

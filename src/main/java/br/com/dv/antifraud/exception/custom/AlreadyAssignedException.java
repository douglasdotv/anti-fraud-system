package br.com.dv.antifraud.exception.custom;

public class AlreadyAssignedException extends RuntimeException {

    public AlreadyAssignedException(String message) {
        super(message);
    }

}

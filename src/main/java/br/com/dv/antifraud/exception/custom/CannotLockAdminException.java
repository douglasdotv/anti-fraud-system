package br.com.dv.antifraud.exception.custom;

public class CannotLockAdminException extends RuntimeException {

    public CannotLockAdminException(String message) {
        super(message);
    }

}

package br.com.dv.antifraud.exception;

public class CannotLockAdminException extends RuntimeException {

    public CannotLockAdminException(String message) {
        super(message);
    }

}

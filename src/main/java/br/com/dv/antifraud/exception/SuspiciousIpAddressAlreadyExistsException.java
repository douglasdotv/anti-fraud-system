package br.com.dv.antifraud.exception;

public class SuspiciousIpAddressAlreadyExistsException extends RuntimeException {

    public SuspiciousIpAddressAlreadyExistsException(String message) {
        super(message);
    }

}

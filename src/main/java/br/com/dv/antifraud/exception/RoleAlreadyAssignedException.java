package br.com.dv.antifraud.exception;

public class RoleAlreadyAssignedException extends RuntimeException {

    public RoleAlreadyAssignedException(String message) {
        super(message);
    }

}

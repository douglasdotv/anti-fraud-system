package br.com.dv.antifraud.exception.custom;

public class FeedbackAlreadyAssignedException extends RuntimeException {

    public FeedbackAlreadyAssignedException(String message) {
        super(message);
    }

}

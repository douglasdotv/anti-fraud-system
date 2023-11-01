package br.com.dv.antifraud.exception.custom;

public class SameFeedbackAndResultException extends RuntimeException {

    public SameFeedbackAndResultException(String message) {
        super(message);
    }

}

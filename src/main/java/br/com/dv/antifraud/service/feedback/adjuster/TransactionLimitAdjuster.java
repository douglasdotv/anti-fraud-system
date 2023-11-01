package br.com.dv.antifraud.service.feedback.adjuster;

import org.springframework.stereotype.Component;

@Component
public class TransactionLimitAdjuster {

    private static final double INCREASE_FACTOR = 0.2;
    private static final double DECREASE_FACTOR = 0.2;
    private static final double BASE_FACTOR = 0.8;

    public Long increaseLimit(Long transactionValue, Long currentLimit) {
        return (long) Math.ceil(BASE_FACTOR * currentLimit + INCREASE_FACTOR * transactionValue);
    }

    public Long decreaseLimit(Long transactionValue, Long currentLimit) {
        return (long) Math.ceil(BASE_FACTOR * currentLimit - DECREASE_FACTOR * transactionValue);
    }

}

package br.com.dv.antifraud.service.feedback.strategy;

import br.com.dv.antifraud.dto.transaction.FeedbackRequest;
import br.com.dv.antifraud.entity.Transaction;

public interface FeedbackStrategy {

    boolean isApplicable(FeedbackRequest request, Transaction transaction);

    void apply(FeedbackRequest request, Transaction transaction);

}

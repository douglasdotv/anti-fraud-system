package br.com.dv.antifraud.service.feedback;

import br.com.dv.antifraud.dto.transaction.FeedbackRequest;
import br.com.dv.antifraud.dto.transaction.TransactionResponse;

public interface FeedbackService {

    TransactionResponse addFeedback(FeedbackRequest request);

}

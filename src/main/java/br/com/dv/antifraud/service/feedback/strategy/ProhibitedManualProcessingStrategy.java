package br.com.dv.antifraud.service.feedback.strategy;

import br.com.dv.antifraud.dto.transaction.FeedbackRequest;
import br.com.dv.antifraud.entity.Card;
import br.com.dv.antifraud.entity.Transaction;
import br.com.dv.antifraud.enums.TransactionResult;
import br.com.dv.antifraud.repository.CardRepository;
import br.com.dv.antifraud.service.feedback.adjuster.TransactionLimitAdjuster;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import static br.com.dv.antifraud.constants.AntifraudSystemConstants.CARD_NOT_FOUND_MESSAGE;

@Component
public class ProhibitedManualProcessingStrategy implements FeedbackStrategy {

    private final CardRepository repository;
    private final TransactionLimitAdjuster limitAdjuster;

    public ProhibitedManualProcessingStrategy(CardRepository repository,
                                              TransactionLimitAdjuster limitAdjuster) {
        this.repository = repository;
        this.limitAdjuster = limitAdjuster;
    }

    @Override
    public boolean isApplicable(FeedbackRequest request, Transaction transaction) {
        return request.feedback() == TransactionResult.PROHIBITED
                && transaction.getResult() == TransactionResult.MANUAL_PROCESSING;
    }

    @Override
    public void apply(FeedbackRequest request, Transaction transaction) {
        Card card = repository.findByCardNumber(transaction.getCardNumber())
                .orElseThrow(() -> new EntityNotFoundException(CARD_NOT_FOUND_MESSAGE));

        Long manualProcessingMax = card.getManualProcessingMax();
        Long newManualProcessingMax = limitAdjuster.decreaseLimit(transaction.getAmount(), manualProcessingMax);

        card.setManualProcessingMax(newManualProcessingMax);
        card.setProhibitedMin(newManualProcessingMax + 1);

        repository.save(card);
    }

}

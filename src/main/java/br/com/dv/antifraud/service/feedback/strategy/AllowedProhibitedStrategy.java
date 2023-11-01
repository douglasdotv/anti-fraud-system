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
public class AllowedProhibitedStrategy implements FeedbackStrategy {

    private final CardRepository repository;
    private final TransactionLimitAdjuster limitAdjuster;

    public AllowedProhibitedStrategy(CardRepository repository,
                                     TransactionLimitAdjuster limitAdjuster) {
        this.repository = repository;
        this.limitAdjuster = limitAdjuster;
    }

    @Override
    public boolean isApplicable(FeedbackRequest request, Transaction transaction) {
        return request.feedback() == TransactionResult.ALLOWED
                && transaction.getResult() == TransactionResult.PROHIBITED;
    }

    @Override
    public void apply(FeedbackRequest request, Transaction transaction) {
        Card card = repository.findByCardNumber(transaction.getCardNumber())
                .orElseThrow(() -> new EntityNotFoundException(CARD_NOT_FOUND_MESSAGE));

        Long allowedMax = card.getAllowedMax();
        Long newAllowedMax = limitAdjuster.increaseLimit(transaction.getAmount(), allowedMax);

        Long manualProcessingMax = card.getManualProcessingMax();
        Long newManualProcessingMax = limitAdjuster.increaseLimit(transaction.getAmount(), manualProcessingMax);

        card.setAllowedMax(newAllowedMax);
        card.setManualProcessingMin(newAllowedMax + 1);

        card.setManualProcessingMax(newManualProcessingMax);
        card.setProhibitedMin(newManualProcessingMax + 1);

        repository.save(card);
    }

}

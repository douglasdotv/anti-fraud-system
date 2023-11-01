package br.com.dv.antifraud.service.transaction.strategy;

import br.com.dv.antifraud.constants.AntifraudSystemConstants;
import br.com.dv.antifraud.dto.transaction.TransactionRequest;
import br.com.dv.antifraud.enums.TransactionInfo;
import br.com.dv.antifraud.enums.TransactionResult;
import br.com.dv.antifraud.repository.CardRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import static br.com.dv.antifraud.constants.AntifraudSystemConstants.CARD_NOT_FOUND_MESSAGE;

@Component
public class AmountProhibitedCheck implements TransactionCheck {

    private final CardRepository repository;

    public AmountProhibitedCheck(CardRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean matchesCondition(TransactionRequest request) {
        var card = repository.findByCardNumber(request.number())
                .orElseThrow(() -> new EntityNotFoundException(CARD_NOT_FOUND_MESSAGE));

        return request.amount() >= card.getProhibitedMin();
    }

    @Override
    public TransactionResult getResult() {
        return TransactionResult.PROHIBITED;
    }

    @Override
    public String getInfo() {
        return TransactionInfo.AMOUNT.getValue();
    }

    @Override
    public int getSeverity() {
        return AntifraudSystemConstants.SEVERITY_PROHIBITED;
    }

}

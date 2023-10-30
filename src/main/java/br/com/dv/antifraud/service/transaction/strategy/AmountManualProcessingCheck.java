package br.com.dv.antifraud.service.transaction.strategy;

import br.com.dv.antifraud.dto.transaction.TransactionRequest;
import br.com.dv.antifraud.enums.TransactionInfo;
import br.com.dv.antifraud.enums.TransactionResult;
import org.springframework.stereotype.Component;

@Component
public class AmountManualProcessingCheck implements TransactionCheck {

    private static final int MANUAL_PROCESSING_MIN_AMOUNT = 201;
    private static final int MANUAL_PROCESSING_MAX_AMOUNT = 1500;

    @Override
    public boolean matchesCondition(TransactionRequest request) {
        return request.amount() >= MANUAL_PROCESSING_MIN_AMOUNT && request.amount() <= MANUAL_PROCESSING_MAX_AMOUNT;
    }

    @Override
    public TransactionResult getResult() {
        return TransactionResult.MANUAL_PROCESSING;
    }

    @Override
    public String getInfo() {
        return TransactionInfo.AMOUNT.getValue();
    }

    @Override
    public int getSeverity() {
        return 1;
    }

}

package br.com.dv.antifraud.service.transaction.strategy;

import br.com.dv.antifraud.constants.AntifraudSystemConstants;
import br.com.dv.antifraud.dto.transaction.TransactionRequest;
import br.com.dv.antifraud.enums.TransactionInfo;
import br.com.dv.antifraud.enums.TransactionResult;
import org.springframework.stereotype.Component;

@Component
public class AmountManualProcessingCheck implements TransactionCheck {

    @Override
    public boolean matchesCondition(TransactionRequest request) {
        return request.amount() >= AntifraudSystemConstants.LOWER_LIMIT_AMOUNT_MANUAL_PROCESSING &&
                request.amount() <= AntifraudSystemConstants.UPPER_LIMIT_AMOUNT_MANUAL_PROCESSING;
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
        return AntifraudSystemConstants.SEVERITY_MANUAL_PROCESSING;
    }

}

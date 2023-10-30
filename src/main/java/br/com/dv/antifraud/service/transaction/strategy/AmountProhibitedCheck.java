package br.com.dv.antifraud.service.transaction.strategy;

import br.com.dv.antifraud.constants.AntifraudSystemConstants;
import br.com.dv.antifraud.dto.transaction.TransactionRequest;
import br.com.dv.antifraud.enums.TransactionInfo;
import br.com.dv.antifraud.enums.TransactionResult;
import org.springframework.stereotype.Component;

@Component
public class AmountProhibitedCheck implements TransactionCheck {

    @Override
    public boolean matchesCondition(TransactionRequest request) {
        return request.amount() >= AntifraudSystemConstants.LOWER_LIMIT_AMOUNT_PROHIBITED;
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

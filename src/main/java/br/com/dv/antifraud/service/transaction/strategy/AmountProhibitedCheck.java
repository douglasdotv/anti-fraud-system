package br.com.dv.antifraud.service.transaction.strategy;

import br.com.dv.antifraud.dto.transaction.TransactionRequest;
import br.com.dv.antifraud.enums.TransactionInfo;
import br.com.dv.antifraud.enums.TransactionResult;
import org.springframework.stereotype.Component;

@Component
public class AmountProhibitedCheck implements TransactionCheck {

    private static final int PROHIBITED_MIN_AMOUNT = 1501;

    @Override
    public boolean matchesCondition(TransactionRequest request) {
        return request.amount() >= PROHIBITED_MIN_AMOUNT;
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
        return 2;
    }

}

package br.com.dv.antifraud.service.transaction.strategy;

import br.com.dv.antifraud.dto.transaction.TransactionRequest;
import br.com.dv.antifraud.enums.TransactionInfo;
import br.com.dv.antifraud.enums.TransactionResult;
import org.springframework.stereotype.Component;

@Component
public class AmountAllowedCheck implements TransactionCheck {

    private static final int ALLOWED_MAX_AMOUNT = 200;

    @Override
    public boolean matchesCondition(TransactionRequest request) {
        return request.amount() <= ALLOWED_MAX_AMOUNT;
    }

    @Override
    public TransactionResult getResult() {
        return TransactionResult.ALLOWED;
    }

    @Override
    public String getInfo() {
        return TransactionInfo.NONE.getValue();
    }

    @Override
    public int getSeverity() {
        return 0;
    }

}

package br.com.dv.antifraud.service.transaction.strategy;

import br.com.dv.antifraud.dto.transaction.TransactionRequest;
import br.com.dv.antifraud.enums.TransactionResult;

public interface TransactionCheck {

    boolean matchesCondition(TransactionRequest transaction);

    TransactionResult getResult();

    String getInfo();

    int getSeverity();

}

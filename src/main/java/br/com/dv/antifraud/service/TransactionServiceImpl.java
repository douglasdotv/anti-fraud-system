package br.com.dv.antifraud.service;

import br.com.dv.antifraud.dto.TransactionInfo;
import br.com.dv.antifraud.dto.TransactionResponse;
import br.com.dv.antifraud.enums.TransactionResult;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Override
    public TransactionResponse processTransaction(TransactionInfo transactionInfo) {
        TransactionResult result = TransactionResult.fromAmount(transactionInfo.amount());
        return new TransactionResponse(result.toString());
    }

}
